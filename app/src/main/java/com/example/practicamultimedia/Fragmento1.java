package com.example.practicamultimedia;

import android.content.Context;
import android.graphics.Color;
import android.media.AudioAttributes;
import android.media.AudioManager;
import android.media.Image;
import android.media.SoundPool;
import android.os.Build;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

import static android.content.Context.AUDIO_SERVICE;

public class Fragmento1 extends Fragment implements View.OnClickListener {

    private View vista_principal = null;
    private Context context = null;
    private int ultimaImagenSeleccionada = 0;
    // Métodos importantes de HashMap -> get (value a partir de key) y put (value con key determinada)
    private HashMap<Integer,DatosImagen> listaClaveValor = new HashMap<>();
    private Integer[] listaAnimales = null;
    private String resId = "imagen";
    private TextView contador;

    // SoundPool

    private SoundPool soundPool;

    // identificadores de canales de sonido

    private int id_error,
                id_nivel_completado,
                id_acierto_individual,
                id_acierto_pareja;

    // animation
    private Animation animationGirar;

    // volumen

    private float mVolume = 0f;

    public static Fragmento1 newInstance(){
        Fragmento1 fragmento1 = new Fragmento1();
        return fragmento1;
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        this.context = context;
    }

    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        View view = inflater.inflate(R.layout.fragment1,container,false);
        return view;
    }

    // Se le llama cuando sabemos que se ha creado correctamente el fragmento

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        vista_principal = getView();
        Button boton1 = (Button) vista_principal.findViewById(R.id.botonEmpezar);
        boton1.setOnClickListener(this);
        Button boton2 = (Button) vista_principal.findViewById(R.id.botonAcabar);
        boton2.setOnClickListener(this);
        contador = (TextView) vista_principal.findViewById(R.id.contadorAciertos);

        inicializarSoundPool();
        animationGirar = AnimationUtils.loadAnimation(context,R.anim.girar_imagen);


    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        soundPool.release();
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()){
            case R.id.botonEmpezar:
                empezarPartida();
                break;
            case R.id.botonAcabar:
                acabarPartida();
                break;
            default:
                comprobarImagen(v.getId());
        }
    }

    /*
     *  Método para configurar los elementos de la partida
     *  Entrada: void
     *  Salida: void
     *
     * */

    public void empezarPartida(){

        asignarAnimales();
        Button boton1 = (Button) vista_principal.findViewById(R.id.botonEmpezar);
        boton1.setEnabled(false);
        Button boton2 = (Button) vista_principal.findViewById(R.id.botonAcabar);
        boton2.setEnabled(true);


    }

    /*
     *  Método para asignar los animales a las vistas en el momento de empezar la partida
     *  Entrada: int id : identificador del id de pareja pulsado anteriormente
     *           int nuevoId: identificador del id de pareja nuevo
     *  Salida: void
     *
     * */

    public void anadirPareja(int id, int nuevoId){
        listaClaveValor.put(id, new DatosImagen(false,nuevoId));
        DatosImagen datosImagen = listaClaveValor.get(nuevoId);
        datosImagen.setId_pareja(id);
        listaClaveValor.put(nuevoId,datosImagen);

    }


    /*
    *  Método para asignar los animales a las vistas en el momento de empezar la partida
    *  Entrada: void
    *  Salida: void
    *
    * */

    public void asignarAnimales(){

        listaAnimales = new Integer[]{1,1,2,2,3,3,4,4,5,5,6,6};
        Integer[] valores = new Integer[]{R.drawable.conejo,R.drawable.ciervo,R.drawable.erizo,R.drawable.oso,R.drawable.pajaro,R.drawable.zorro};
        List<Integer> lista = Arrays.asList(listaAnimales);
        Collections.shuffle(lista);
        listaAnimales = (Integer[]) lista.toArray();
        ImageView imageView = null;

        int conejo = 0;
        int ciervo = 0;
        int erizo = 0;
        int oso = 0;
        int pajaro = 0;
        int zorro = 0;

        for(int i=0; i<listaAnimales.length;i++){

            // integer con el identificador de la imagen i

            //getResources().getIdentifier(name,type,pasarle a partir de qué paquete se hace)
            int id = getResources().getIdentifier(resId+(i+1),"id",context.getPackageName());
            imageView = vista_principal.findViewById(id);

            switch (listaAnimales[i]){
                case 1: // caso conejo
                    imageView.setImageResource(valores[0]);
                    if(conejo == 0){
                        listaClaveValor.put(id,new DatosImagen(false,1));
                        conejo = id;
                    }
                    else{
                        anadirPareja(id, conejo);
                    }
                    break;
                case 2: // caso ciervo
                    imageView.setImageResource(valores[1]);
                    if(ciervo == 0){
                        listaClaveValor.put(id,new DatosImagen(false,1));
                        ciervo = id;
                    }
                    else{
                        anadirPareja(id, ciervo);
                    }
                    break;
                case 3: // caso erizo
                    imageView.setImageResource(valores[2]);
                    if(erizo == 0){
                        listaClaveValor.put(id,new DatosImagen(false,1));
                        erizo = id;
                    }
                    else{
                        anadirPareja(id, erizo);
                    }
                    break;
                case 4: // caso oso
                    imageView.setImageResource(valores[3]);
                    if(oso == 0){
                        listaClaveValor.put(id,new DatosImagen(false,1));
                        oso = id;
                    }
                    else{
                        anadirPareja(id, oso);
                    }
                    break;
                case 5: // caso pajaro
                    imageView.setImageResource(valores[4]);
                    if(pajaro == 0){
                        listaClaveValor.put(id,new DatosImagen(false,1));
                        pajaro = id;
                    }
                    else{
                        anadirPareja(id, pajaro);
                    }
                    break;
                default: // caso zorro
                    imageView.setImageResource(valores[5]);
                    if(zorro == 0){
                        listaClaveValor.put(id,new DatosImagen(false,1));
                        zorro = id;
                    }
                    else{
                        anadirPareja(id, zorro);
                    }
            }

            imageView.setOnClickListener(this);
            imageView.setClickable(true);

        }

    }

    /*
     *  Método para comprobar la imagen que ha sido pulsada y actuar en consecuencia
     *  Entrada: void
     *  Salida: void
     *
     * */

    public void comprobarImagen(int id){

        DatosImagen datosImagenPrincipal = listaClaveValor.get(id);
        AudioManager audioManager = (AudioManager) getActivity().getSystemService(AUDIO_SERVICE);
        float actualVolume = (float) audioManager.getStreamVolume(AudioManager.STREAM_MUSIC);
        float maxVolume = (float) audioManager.getStreamMaxVolume(AudioManager.STREAM_MUSIC);
        mVolume = actualVolume / maxVolume;
        // Si está clicado, tendremos que comprobar si la pareja lo está

        // Primero... Comprobamos si hay otro clicado...

        if(ultimaImagenSeleccionada != 0){

            // deseleccionamos...

            if(ultimaImagenSeleccionada == id){

                ImageView imageView = vista_principal.findViewById(id);
                imageView.setBackgroundColor(ContextCompat.getColor(context,R.color.cardview_dark_background));
                soundPool.play(id_error,mVolume,mVolume,1,0,1f);
            }
            else{
                if(ultimaImagenSeleccionada == datosImagenPrincipal.getId_pareja()){

                    ImageView imageView = (ImageView) vista_principal.findViewById(id);
                    imageView.setBackgroundColor(Color.YELLOW);
                    imageView.setClickable(false);
                    imageView.startAnimation(animationGirar);
                    ImageView imageView1 = (ImageView) vista_principal.findViewById(datosImagenPrincipal.getId_pareja());
                    imageView1.setClickable(false);
                    imageView1.startAnimation(animationGirar);
                    int cuenta = Integer.valueOf(contador.getText().toString());
                    cuenta++;
                    contador.setText(String.valueOf(cuenta));


                    if(cuenta == 6){

                        soundPool.play(id_nivel_completado,mVolume,mVolume,1,0,1f);
                    }
                    else{
                        soundPool.play(id_acierto_pareja,mVolume,mVolume,1,0,1f);

                    }

                }
                // caso de que no sea ni la propia vista ni la de la pareja, cambia el bg de la ultima y de la actual
                // y lanza sonido de ERROR
                else{
                    ImageView imageView = vista_principal.findViewById(id);
                    imageView.setBackgroundColor(ContextCompat.getColor(context,R.color.cardview_dark_background));
                    ImageView imageView1 = vista_principal.findViewById(ultimaImagenSeleccionada);
                    imageView1.setBackgroundColor(ContextCompat.getColor(context,R.color.cardview_dark_background));
                    soundPool.play(id_error,mVolume,mVolume,1,0,1f);

                }
            }

            ultimaImagenSeleccionada = 0;

        }
        // en el caso de que sea la primera seleccionada, cambiar fondo y poner sonido de MONEDA
        else{

            ImageView imageView = (ImageView) vista_principal.findViewById(id);
            imageView.setBackgroundColor(Color.YELLOW);
            ultimaImagenSeleccionada = id;
            soundPool.play(id_acierto_individual,mVolume,mVolume,1,0,1f);

        }
    }

    /*
     *  Método para devolver al fragmento a su estado inicial
     *  Entrada: void
     *  Salida: void
     *
     * */

    public void acabarPartida(){

        ImageView imageView;

        for(int i=0; i<listaAnimales.length;i++){

            int id = getResources().getIdentifier(resId+(i+1),"id",context.getPackageName());
            imageView = vista_principal.findViewById(id);
            imageView.setImageBitmap(null);
            imageView.setBackgroundColor(ContextCompat.getColor(context,R.color.cardview_dark_background));

        }

        Button boton1 = (Button) vista_principal.findViewById(R.id.botonEmpezar);
        boton1.setEnabled(true);
        Button boton2 = (Button) vista_principal.findViewById(R.id.botonAcabar);
        boton2.setEnabled(false);
        contador.setText(String.valueOf(0));

    }

    /*
     *  Método para definir los atributos principales de SoundPool y almacenar los identificadores de streams
     *  Entrada: void
     *  Salida: void
     *
     * */

    public void inicializarSoundPool(){

        // AudioAttributes -> Definen información sobre el stream de audio

        AudioAttributes audioAttributes = new AudioAttributes.Builder()
                .setUsage(AudioAttributes.USAGE_ASSISTANCE_SONIFICATION)
                .setContentType(AudioAttributes.CONTENT_TYPE_SONIFICATION)
                .build();
        soundPool = new SoundPool.Builder()
                .setMaxStreams(3)
                .setAudioAttributes(audioAttributes)
                .build();

        id_acierto_individual = soundPool.load(context,R.raw.mario_coin,1);
        id_acierto_pareja = soundPool.load(context,R.raw.mario_bros_woo_hoo,1);
        id_error = soundPool.load(context,R.raw.mario_bros_firework,1);
        id_nivel_completado = soundPool.load(context,R.raw.mario_bros_1_up,1);

        getActivity().setVolumeControlStream(AudioManager.STREAM_MUSIC);


    }

}
