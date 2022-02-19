package com.example.terceratarea;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;
import android.widget.Toast;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    Spinner categorias;
    String categoria;
    Button visualizar, editar;
    DaoChiste dao;
    BDChiste bd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //se obtiene la base de datos y su Dao
        bd = BDChiste.obtenerBD(this);
        dao = bd.obtenerDao();

        /*
        se comprueba si existe el archivo de almacenamiento de la base de datos. Si no es así,
        se llama al método cargarDatos() para establecer los datos por defecto en la app
         */
        String ruta = "/data/data/com.example.terceratarea/databases/BDChiste";
        if (new File(ruta).exists() == false)
            cargarDatos();

        visualizar = findViewById(R.id.buttonVisualizar);
        visualizar.setEnabled(false);
        editar = findViewById(R.id.buttonEditar);
        editar.setEnabled(false);

        categorias = findViewById(R.id.spinnerCategorias);

        //se crea el adaptador con las categorias
        ArrayAdapter adaptador = ArrayAdapter.createFromResource(this, R.array.categorias,
                android.R.layout.simple_spinner_dropdown_item);
        categorias.setAdapter(adaptador);

        //Listener del Spinner
        categorias.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int pos, long id) {
                switch (pos) {
                    case 1:
                        categoria = "sexo";
                        visualizar.setEnabled(true);
                        editar.setEnabled(true);
                        break;
                    case 2:
                        categoria = "informatica";
                        visualizar.setEnabled(true);
                        editar.setEnabled(true);
                        break;
                    case 3:
                        categoria = "manyos";
                        visualizar.setEnabled(true);
                        editar.setEnabled(true);
                        break;
                    default:
                        visualizar.setEnabled(false);
                        editar.setEnabled(false);
                        editar = findViewById(R.id.buttonEditar);
                        break;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }

    //lleva a una actividad dependiendo del boton pulsado en la interfaz
    public void aActivity(View vista) {
        Intent intent;
        switch (vista.getId()) {
            case R.id.buttonVisualizar:
                intent = new Intent(this, Visualizar.class);
                if (dao.numeroChistes(categoria) > 0) {
                    intent.putExtra("categoria", categoria);
                    startActivity(intent);
                }
                else {
                    Toast.makeText(this, R.string.main_toast_sin_resultados,
                            Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.buttonEditar:
                intent = new Intent(this, Editar.class);
                intent.putExtra("categoria", categoria);
                startActivity(intent);
                break;
            default:
                intent = new Intent(this, MainActivity.class);
                startActivity(intent);
        }
    }

    //método con los datos predeterminados de la base de datos.
    public void cargarDatos() {
        List<Chiste> chistes = new ArrayList<>();
        chistes.add(new Chiste("sexo", "¡Hombres!", "Los 5 secretos de una buena relación de pareja:\n" +
                "\n" +
                "1.. Es importante encontrar a una mujer que sepa cocinar, limpiar, hacer cositas en casa y que tenga trabajo.\n" +
                "\n" +
                "2.. Es importante encontrar a una mujer con la que puedas contar y que no mienta.\n" +
                "\n" +
                "3.. Es importante encontrar a una mujer que sea buena en la cama y que le encante hacer el amor contigo.\n" +
                "\n" +
                "4.. Es importante encontrar a una mujer que sea divertida y te haga reír.\n" +
                "\n" +
                "5.. Es importante que estas cuatro mujeres nunca se conozcan."));
        chistes.add(new Chiste("sexo", "¡Monjitas!", "La novicia del convento esta escribiendo una carta y comienza:\n" +
                "\n" +
                "Querido Monseñor:\n" +
                "\n" +
                "Se da cuenta de que aquel puede mal interpretar sus palabras y vuelve a empezar la carta.\n" +
                "\n" +
                "Excelentísimo Monseñor:\n" +
                "\n" +
                "Recapacita pensando que es demasiado formal.\n" +
                "\n" +
                "Sr. Monseñor:\n" +
                "\n" +
                "Este título le parece muy mundano.\n" +
                "\n" +
                "Así que probando varios títulos, decide que el mejor es Don Monseñor\n" +
                "\n" +
                "Para asegurarse de no meter la pata, le pregunta a la Madre Superiora:\n" +
                "\n" +
                "¿Madre, Monseñor se pone con Don?\n" +
                "\n" +
                "- Claro que se pone condón, si no, este convento sería una guardería"));
        chistes.add(new Chiste("sexo", "¡Qué hijo de la gran...!", "Un niño de unos 13 años va por la calle arrastrando de una cuerda a una rana aplastada.\n" +
                "\n" +
                "Llega a uno de esos locales de mala reputación que tienen una luz roja en la entrada y llama a la puerta. La vieja portera abre y al verlo se sorprende. Este le dice que quiere hacerlo con una de las chicas y le muestra un fajo de dólares. La vieja mira el dinero, acepta y le invita a elegir entre las chicas la que más le guste. El niño pregunta si alguna de ellas tiene alguna enfermedad, por supuesto la vieja responde que no. Pero él había oído a los hombres del pueblo decir que habían tenido que ir al hospital a recibir tratamiento después de haberlo hecho con María y esa era la chica que quería. Visto que el niño estaba tan decidido y tenía dinero, la vieja le dice que María está en el primer piso. Sube la escalera arrastrando la rana aplastada. A los diez minutos baja arrastrando la rana, le paga a la vieja y al dirigirse hacia la salida, ésta le pregunta por qué eligió a la que estaba enferma. El niño responde:\n" +
                "\n" +
                "- Bueno, esta noche cuando llegue a casa, mis padres van a salir a cenar y me van a dejar con la niñera. Cuando se hayan ido lo voy a hacer con la niñera que le gustan mucho los jovencitos y ella se contagiará con la enfermedad que yo acabo de agarrar. Cuando vuelvan mis padres, papá llevará a la niñera a su casa y en el camino lo hará con ella y se contagiará la enfermedad. Cuando papá vuelva de llevar a la niñera, él y mamá se acostarán, lo harán y ella también se contagiará. Por la mañana cuando papá se vaya al trabajo, el cartero traerá el correo y se echará un rapidín con mamá y también lo contagiará y ESE, ¡ESE es el hijo de puta al que quiero joder porque atropelló a mi rana!"));
        chistes.add(new Chiste("sexo", "¡Vas a sufriiiiir!", "¡VAS A SUFRIIIIIR!\n" +
                "\n" +
                "Resulta que, tras el diluvio universal, el arca de Noé se movía para todos lados, y el patriarca Noé no encontraba explicación a ello.\n" +
                "\n" +
                "Un día decide ir a visitar la cubierta de los animales, y he ahí el problema: todos los animales hacían el amor.\n" +
                "\n" +
                "Noé enfadado les gritó:\n" +
                "\n" +
                "- Paren, ¡esto no puede ser! Les salvé la vida, ¿es así como me pagan? Van a hundir el arca.\n" +
                "\n" +
                "Todos los animales le obedecieron, pero a Noé le dio lástima y les dijo:\n" +
                "\n" +
                "- Le daré una ficha a cada pareja; en ella estará el día y la hora en que pueden hacer el amor.\n" +
                "\n" +
                "Y así lo hizo.\n" +
                "\n" +
                "Pasaron los días y andaba el mono molestando a la mona y le decía:\n" +
                "\n" +
                "- ¡El miércoles a las 4 de la tarde vas a sufrir!\n" +
                "\n" +
                "Y durante tres días le dijo lo mismo.\n" +
                "\n" +
                "La mona, muy enfadada, fue a hablar con Noé.\n" +
                "\n" +
                "- Mira Noé, El mono hace tres días que me anda molestando. Me dice que el miércoles a las 4 de la tarde voy a sufrir. Yo sé lo que va pasar ese día. ¡Pero no puede andar gritándolo por todas partes! ¿Qué van a decir mis amigas?\n" +
                "\n" +
                "Noé, enfadado, fue a buscar al mono y le dijo:\n" +
                "\n" +
                "- ¡Oye, mono...! ¿Por qué molestas a la mona de esa manera? ¿Qué es eso de que va a sufrir?\n" +
                "\n" +
                "- Verás... ¡es que perdí mi ficha jugando al póquer con el burro!"));
        chistes.add(new Chiste("sexo", "¡Ya era hora!", "La isla desierta:\n" +
                "\n" +
                "Un avión se estrella en el pacifico sur.... Sólo sobreviven tres: el piloto, un auxiliar de vuelo y una azafata que se agarran a los restos del avión. Al cabo de una semana a la deriva llegan a una isla desierta, lejos de cualquier ruta aérea y marítima. Saben que no les buscarán más.\n" +
                "\n" +
                "Entonces se organizan la vida, construyen una bonita cabaña, la naturaleza es generosa y les provee de carne, frutas y agua fresca. Ellos son jóvenes y majos... Al cabo de dos meses de convivencia en la isla, la azafata se decide a hablar de un tema con los otros dos...\n" +
                "\n" +
                "- Vamos a ver amigos... Estamos solos... Y puede ser para siempre. Nos hemos respetado desde el momento en que llegamos aquí... Tenemos nuestra intimidad... Todo está fenomenal... Pero... Creo que todos tenemos cierta carencia... Yo sé que vosotros por delicadeza conmigo no queréis hablar de eso por lo tanto lo hago yo: a ver si estáis de acuerdo en esto... Tú me lo haces los días pares y tu los impares... Y si surge cualquier problema lo hablamos y lo solucionamos.\n" +
                "\n" +
                "Todos de acuerdo y encantados de haber estado tan organizados y poder hablar del asunto...\n" +
                "\n" +
                "Pasan unas semanas fabulosas... Cada uno su turno: uno los días pares y otro los impares... Con un respeto y un entendimiento ejemplares. Por desgracia, al cabo de dos meses la chica pilla un virus y se muere. Los dos robinsones se quedan terriblemente tristes... Es una desgracia pero la vida continua y vuelven a la rutina de antes... Al cabo de un mes uno de ellos se dirige al otro y le dice:\n" +
                "\n" +
                "- Oye tío, el tiempo pasa yo sé que esto es tan duro para ti como para mí por eso tenemos que hablar... Me falta alguna cosa... Yo soy joven y no puedo seguir así... ¿Tú que piensas?\n" +
                "\n" +
                "El otro le dio las gracias por sacar el tema y le dice que él también está pasando por la misma situación...\n" +
                "\n" +
                "- Entonces ¿tú también piensas como yo?\n" +
                "\n" +
                "- Pues sí... Y si no funciona lo discutimos.\n" +
                "\n" +
                "- De acuerdo... Entonces, como nos lo montamos?\n" +
                "\n" +
                "- Tú los días pares y yo los impares.\n" +
                "\n" +
                "- Pues vale... No hay problema.\n" +
                "\n" +
                "Y los tíos pasan otro montón de semanas geniales... Pero una noche uno le dice al otro:\n" +
                "\n" +
                "- Oye tío, dijimos que lo discutiríamos si algo no iba bien... Bien, pues yo pienso que esto no debe continuar... Estamos solos y necesitados, pero lo que estamos haciendo no me convence... Es contra natura.\n" +
                "\n" +
                "- Me tranquilizas - le dice el otro -Yo también estaba pensando como tú... me gustaría que parásemos... De todas formas ya no son las mismas sensaciones que antes...\n" +
                "\n" +
                "- Estás de acuerdo entonces?\n" +
                "\n" +
                "- Sí, y ¿tú?.\n" +
                "\n" +
                "- Yo también.\n" +
                "\n" +
                "- Bueno, entonces....... la enterramos, no?"));
        chistes.add(new Chiste("manyos", "Dos zaragozanos", "Le contaba un zaragozano a otro:\n" +
                "\n" +
                "- Íbamos yo y Pepe.\n" +
                "\n" +
                "- No, íbamos Pepe y yo.\n" +
                "\n" +
                "- Vale, listo, yo no iba. "));
        chistes.add(new Chiste("manyos", "Eclipse en Zaragoza", "Un zaragozano pregunta a su padre:\n" +
                "\n" +
                "- Co, papa ¿Puedo salir a ver el eclipse?\n" +
                "\n" +
                "- Está bien hijo, pero no te acerques demasiado. "));
        chistes.add(new Chiste("manyos", "El barbastrense", "Esto es un Barbastrense (Ojo con los cachondeos ¡eh!), que está en la estación del tren y le pregunta al revisor:\n" +
                "\n" +
                "- Oiga, el tren que va a la zona norte. ¿A qué hora sale?\n" +
                "\n" +
                "- A las 13:55\n" +
                "\n" +
                "- ¡Ah! ¿Y el tren que va para Levante?\n" +
                "\n" +
                "- A las 12:17\n" +
                "\n" +
                "- ¿Y el tren que va para Portugal?\n" +
                "\n" +
                "- A las 16:35\n" +
                "\n" +
                "- ¡Oiga! ¿Y el que va a Andalucía?\n" +
                "\n" +
                "- Pero ya está bien. ¿Es que va a coger todos los trenes o qué?\n" +
                "\n" +
                "- ¡No! Yo lo que quiero es cruzar las vías y no tener ningún disgusto."));
        chistes.add(new Chiste("manyos", "El espejico", "Va un almendrón por el parque grande y se encuentra un espejico de cartera.\n" +
                "\n" +
                "Lo abre, se mira y dice:\n" +
                "\n" +
                "- 'Coño, yo a este tío lo conozco ...'\n" +
                "\n" +
                "Se lo guarda.\n" +
                "\n" +
                "De camino a casa, vuelve a mirarse y repite:\n" +
                "\n" +
                "- ¡Joder! ¿De que conozco yo a este tío?, -y se guarda el espejo.\n" +
                "\n" +
                "Mientras Pilar le sirve la comida, se vuelve a mirar:\n" +
                "\n" +
                "- ¡Leche! Yo a este tío le conozco, creo que es el que se corta el pelo enfrente de mí.\n" +
                "\n" +
                "Curiosa Pilar, le pregunta:\n" +
                "\n" +
                "- Oye Paco ¿Qué tienes en la mano?\n" +
                "\n" +
                "- Nada importante.\n" +
                "\n" +
                "Y lo guarda nuevamente en el bolsillo del pantalón.\n" +
                "\n" +
                "Cuando Paco se va a la cama, Pilar intrigada mira en el bolsillo del pantalón y coge el espejo... , se mira en el mismo y dice:\n" +
                "\n" +
                "- Lo sabía ¡Una foto de mujer! ... ¡y que cara de puta tiene!"));
        chistes.add(new Chiste("manyos", "El mañico y la rubia", "Un mañico va en un tren sentado frente a una rubia estupenda, vestida con una minifalda muy corta.\n" +
                "\n" +
                "El mañico intenta no fijarse en sus preciosos muslos, pero es incapaz de retirar su mirada de allí.\n" +
                "\n" +
                "De tanto fisgonear, se acaba dando cuenta de que la chica va sin ropa interior.\n" +
                "\n" +
                "La rubia también se da cuenta de que él la mira justo ahí y le dice:\n" +
                "\n" +
                "- ¿Me estás mirando el chichi, verdad?\n" +
                "\n" +
                "- ¡Sí, disculpa! - responde sonrojado el mañico, tras apartar la mirada.\n" +
                "\n" +
                "- Está bien, no te preocupes - responde la mujer - ¿Sabes? Mi chichi es muy hábil. Mira esto, voy a hacer que te guiñe un ojo.\n" +
                "\n" +
                "Dicho y hecho. El mañico ve asombrado cómo el chichi de la rubia le echa un par de guiños.\n" +
                "\n" +
                "Él, totalmente asombrado, se pregunta qué otras cosas podrá hacer.\n" +
                "\n" +
                "- También puedo hacer que te tire un beso.\n" +
                "\n" +
                "La chica se sube un poco más la falda para que él tenga una vista completa y despejada.\n" +
                "\n" +
                "Acto seguido, el chichi increíblemente, contrae sus labios y le tira un beso con sonido incluido.\n" +
                "\n" +
                "- ¡¡¡Muacccc!!!\n" +
                "\n" +
                "- ¡¡¡Ñoosss!!!\n" +
                "\n" +
                "El mañico no se lo podía ni creer.\n" +
                "\n" +
                "- Ven y siéntate a mi lado - le sugiere la mujer ya entrada en calor.\n" +
                "\n" +
                "El mañico, ni corto ni perezoso, se sienta a su lado.\n" +
                "\n" +
                "La rubia, con voz melosa, le pregunta:\n" +
                "\n" +
                "- ¿Quieres meter dentro un par de dedos?\n" +
                "\n" +
                "Paralizado de asombro, el mañico le responde:\n" +
                "\n" +
                "- ¡¡¡¡No jodas que también silba!!!! "));
        chistes.add(new Chiste("informatica", "Lo último de Apple", "Lo último de Apple.\n" +
                "\n" +
                "Apple anunció hoy que ha desarrollado un implante de seno que puede guardar, reproducir música y escuchar la radio.\n" +
                "\n" +
                "El \"iTit\" costara de U$ 4.990 a U$ 6.990, según la capacidad de memoria, el tamaño de la copa, y los altavoces.\n" +
                "\n" +
                "Esto se considera un importante adelanto psicotecnológico porque las mujeres siempre se están quejando de que los hombres... les miran las tetas, pero no las escuchan. "));
        chistes.add(new Chiste("informatica", "Mujer informática", "Tipos de Mujer según algunos Ingenieros de Sistemas\n" +
                "\n" +
                "Mujer Virus: Cuando menos lo esperas, se instala en tu apartamento y va apoderándose de todos tus espacios. Si intentas desinstalarla, vas a perder muchas cosas; si no lo intentas, pierdes todas.\n" +
                "\n" +
                "Mujer Internet: Hay que pagar para tener acceso a ella.\n" +
                "\n" +
                "Mujer Servidor: Siempre está ocupada cuando la necesitas.\n" +
                "\n" +
                "Mujer Windows: Sabes que tiene muchos fallos, pero no puedes vivir sin ella.\n" +
                "\n" +
                "Mujer Macintosh: Preciosa, infalible y algo cara, no muy compatible con otras... y solo el 5% de los hombres saben la dicha de tenerla.\n" +
                "\n" +
                "Mujer Powerpoint: Ideal para presentarla a la gente en fiestas, convenciones, etcétera.\n" +
                "\n" +
                "Mujer Excel: Dicen que hace muchas cosas, pero tú tan sólo la utilizas para la operación básica.\n" +
                "\n" +
                "Mujer Word: Tiene siempre una sorpresa reservada para ti y no existe nadie en el mundo que le comprenda totalmente.\n" +
                "\n" +
                "Mujer D.O.S.: Todos la tuvieron algún día, pero nadie la quiere ahora.\n" +
                "\n" +
                "Mujer Backup: Tu crees que tiene lo suficiente, pero a la hora de \"vamos a ver\", le falta algo.\n" +
                "\n" +
                "Mujer Scandisk: Sabemos que es buena y que sólo quiere ayudar, pero en el fondo nadie sabe lo que realmente está haciendo.\n" +
                "\n" +
                "Mujer Screensaver: No sirve para nada, pero te divierte.\n" +
                "\n" +
                "Mujer Paintbrush: Puro adobito y nada de sustancia.\n" +
                "\n" +
                "Mujer RAM: Aquella que olvida todo apenas se desconecta.\n" +
                "\n" +
                "Mujer Disco Duro: Se acuerda de todo, todo el tiempo.\n" +
                "\n" +
                "Mujer Mouse: Funciona sólo cuando la arrastras.\n" +
                "\n" +
                "Mujer Multimedia: Hace que todo parezca bonito.\n" +
                "\n" +
                "Mujer Usuario: No hace nada bien y siempre esta haciendo preguntas.\n" +
                "\n" +
                "Mujer E-Mail: de cada diez cosas que dice nueve son tonterías."));
        chistes.add(new Chiste("informatica", "Obsesionado", "- Doctor, cada día estoy más obsesionado con el ordenado01010101111011..."));
        chistes.add(new Chiste("informatica", "Ordenador", "¿Por qué descubren en una oficina que uno de Lepe ha estado usando el ordenador?.\n" +
                "\n" +
                "Porque es el único que borra con \"tipex\" los errores que aparecen en la pantalla."));
        chistes.add(new Chiste("informatica", "Para quien no conoce el concepto de bucle", "Para quien no conoce el concepto de bucle, es un término que crearon los informáticos para definir un enredo de los tantos que se han creado y para lo cual no tienen una explicación sencilla para aclarar el problema.\n" +
                "\n" +
                "Haciendo poco esfuerzo trataré de explicar en pocas palabras este famoso término.\n" +
                "\n" +
                "Se dice que un programa de informática \"entró en un bucle\" como cuando ocurre la siguiente situación:\n" +
                "\n" +
                "El director llama a su secretaria y le dice:\n" +
                "\n" +
                "- Vanesa: Tengo un seminario en Argentina por una semana y quiero que me acompañe para que conozca a mis socios. Haga los preparativos del viaje.\n" +
                "\n" +
                "La secretaria llama al marido:\n" +
                "\n" +
                "- Oye Juan, Voy a viajar al extranjero con el director por una semana. Tendrás que quedarte solo esa semana, querido.\n" +
                "\n" +
                "El marido llama a la amante:\n" +
                "\n" +
                "- Leonor, mi tesoro: La bruja va a viajar al extranjero durante una semana, vamos a pasarnos esa semana juntos, mi reina.\n" +
                "\n" +
                "La amante llama al niño a quien le da clases particulares:\n" +
                "\n" +
                "- Manuelito: Tengo mucho trabajo la próxima semana. No tienes que venir a dar clase.\n" +
                "\n" +
                "El niño llama a su abuelo:\n" +
                "\n" +
                "- Oye abuelo: La próxima semana no tengo clases, mi profesora estará ocupada. Así que por fin. ¡Vamos a poder pasar la semana juntos!\n" +
                "\n" +
                "El abuelo (que es el DIRECTOR en esta historia) llama a la secretaria:\n" +
                "\n" +
                "- Vanesa, venga rápido: Suspenda el viaje, voy a pasar la próxima semana con mi nieto que hace un año no veo, por lo que no vamos a participar en el Seminario. Cancele el viaje y el hotel.\n" +
                "\n" +
                "La secretaria llama al marido:\n" +
                "\n" +
                "- Juan: El payaso del director cambió de idea y acaba de cancelar el viaje, se fastidió el ir a Argentina.\n" +
                "\n" +
                "El marido llama a la amante:\n" +
                "\n" +
                "- Amorcito, disculpa: No podremos pasar la próxima semana juntos, el viaje de la tetona de mi mujer fue cancelado.\n" +
                "\n" +
                "La amante llama al niño de las clases particulares:\n" +
                "\n" +
                "- Manolito: Mira, cambié de planes; esta semana te voy a dar clases como siempre.\n" +
                "\n" +
                "El niño llama al abuelo:\n" +
                "\n" +
                "- Abuelo: la pesada de mi profesora me dijo que esta semana sí tengo clases normales, discúlpame, no voy a poder hacerte compañía.\n" +
                "\n" +
                "El abuelo llama a la secretaria:\n" +
                "\n" +
                "- Vanesa: Mi nieto me acaba de decir que no va a poder estar conmigo esta semana porque tiene clases. Así que continúe con los preparativos del viaje al seminario... "));

        dao.insertarTodo(chistes);
    }
}