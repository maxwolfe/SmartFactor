package com.example.max.smartfactor;

import android.annotation.TargetApi;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Environment;
import android.os.Handler;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.appindexing.Thing;
import com.google.android.gms.common.api.GoogleApiClient;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.lang.reflect.UndeclaredThrowableException;
import java.security.GeneralSecurityException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.math.BigInteger;
import java.util.TimeZone;

public class MainActivity extends AppCompatActivity implements AsyncDelegate {
    protected int mode = 0;
    protected String seed32   = "3132333435363738393031323334353637383930313233343536373839303132";
    protected String seed32_2 = "3132333435363738393031323334353637383930313233343536373839303133";
    protected String seed32_3 = "3132333935363738393031323334353637383930313233343536373839303133";
    protected String seed32_4 = "5132333435363738393031323334353637383930313233343536373839303133";


    protected final char[] CONSONANTS = {'b', 'c', 'd', 'f', 'g', 'h', 'j', 'k', 'l', 'm', 'n', 'p', 'q', 'r', 's', 't', 'v', 'w', 'x', 'z'};
    protected final char[] VOWELS = {'a', 'e', 'i', 'o', 'u'};
    protected final String[] WORDLIST = {"aah", "aal", "aas", "aba", "abo", "abs", "aby", "ace", "act", "add", "ado", "ads", "adz", "aff", "aft", "aga", "age", "ago", "ags", "aha", "ahi", "ahs", "aid", "ail", "aim", "ain", "air", "ais", "ait", "ala", "alb", "ale", "all", "alp", "als", "alt", "ama", "ami", "amp", "amu", "ana", "and", "ane", "ani", "ant", "any", "ape", "apo", "app", "apt", "arb", "arc", "are", "arf", "ark", "arm", "ars", "art", "ash", "ask", "asp", "ass", "ate", "att", "auk", "ava", "ave", "avo", "awa", "awe", "awl", "awn", "axe", "aye", "ays", "azo", "baa", "bad", "bag", "bah", "bal", "bam", "ban", "bap", "bar", "bas", "bat", "bay", "bed", "bee", "beg", "bel", "ben", "bes", "bet", "bey", "bib", "bid", "big", "bin", "bio", "bis", "bit", "biz", "boa", "bob", "bod", "bog", "boo", "bop", "bos", "bot", "bow", "box", "boy", "bra", "bro", "brr", "bub", "bud", "bug", "bum", "bun", "bur", "bus", "but", "buy", "bye", "bys", "cab", "cad", "cam", "can", "cap", "car", "cat", "caw", "cay", "cee", "cel", "cep", "chi", "cig", "cis", "cob", "cod", "cog", "col", "con", "coo", "cop", "cor", "cos", "cot", "cow", "cox", "coy", "coz", "cru", "cry", "cub", "cud", "cue", "cup", "cur", "cut", "cwm", "dab", "dad", "dag", "dah", "dak", "dal", "dam", "dan", "dap", "daw", "day", "deb", "dee", "def", "del", "den", "dev", "dew", "dex", "dey", "dib", "did", "die", "dif", "dig", "dim", "din", "dip", "dis", "dit", "doc", "doe", "dog", "dol", "dom", "don", "dor", "dos", "dot", "dow", "dry", "dub", "dud", "due", "dug", "duh", "dui", "dun", "duo", "dup", "dye", "ear", "eat", "eau", "ebb", "ecu", "edh", "eds", "eek", "eel", "eff", "efs", "eft", "egg", "ego", "eke", "eld", "elf", "elk", "ell", "elm", "els", "eme", "ems", "emu", "end", "eng", "ens", "eon", "era", "ere", "erg", "ern", "err", "ers", "ess", "eta", "eth", "eve", "ewe", "eye", "fab", "fad", "fag", "fan", "far", "fas", "fat", "fax", "fay", "fed", "fee", "feh", "fem", "fen", "fer", "fes", "fet", "feu", "few", "fey", "fez", "fib", "fid", "fie", "fig", "fil", "fin", "fir", "fit", "fix", "fiz", "flu", "fly", "fob", "foe", "fog", "foh", "fon", "fop", "for", "fou", "fox", "foy", "fro", "fry", "fub", "fud", "fug", "fun", "fur", "gab", "gad", "gae", "gag", "gal", "gam", "gan", "gap", "gar", "gas", "gat", "gay", "ged", "gee", "gel", "gem", "gen", "get", "gey", "ghi", "gib", "gid", "gie", "gig", "gin", "gip", "git", "gnu", "goa", "gob", "god", "goo", "gor", "gos", "got", "gox", "goy", "gul", "gum", "gun", "gut", "guv", "guy", "gym", "gyp", "had", "hae", "hag", "hah", "haj", "ham", "hao", "hap", "has", "hat", "haw", "hay", "heh", "hem", "hen", "hep", "her", "hes", "het", "hew", "hex", "hey", "hic", "hid", "hie", "him", "hin", "hip", "his", "hit", "hmm", "hob", "hod", "hoe", "hog", "hon", "hop", "hos", "hot", "how", "hoy", "hub", "hue", "hug", "huh", "hum", "hun", "hup", "hut", "hyp", "ice", "ich", "ick", "icy", "ids", "iff", "ifs", "igg", "ilk", "ill", "imp", "ink", "inn", "ins", "ion", "ire", "irk", "ism", "its", "ivy", "jab", "jag", "jam", "jar", "jaw", "jay", "jee", "jet", "jeu", "jew", "jib", "jig", "jin", "job", "joe", "jog", "jot", "jow", "joy", "jug", "ju    n", "jus", "jut", "kab", "kae", "kaf", "kas", "kat", "kay", "kea", "kef", "keg", "ken", "kep", "kex", "key", "khi", "kid", "kif", "kin", "kip", "kir", "kis", "kit", "koa", "kob", "koi", "kop", "kor", "kos", "kue", "kye", "lab", "lac", "lad", "    lag", "lam", "lap", "lar", "las", "lat", "lav", "law", "lax", "lay", "lea", "led", "lee", "leg", "lei", "lek", "les", "let", "leu", "lev", "lex", "ley", "lez", "lib", "lid", "lie", "lin", "lip", "lis", "lit", "lob", "log", "loo", "lop", "lot", "low", "lox", "lug", "lum", "luv", "lux", "lye", "mac", "mad", "mae", "mag", "man", "map", "mar", "mas", "mat", "maw", "max", "may", "med", "meg", "mel", "mem", "men", "met", "mew", "mho", "mib", "mic", "mid", "mig", "mil", "mim", "mir", "mi    s", "mix", "moa", "mob", "moc", "mod", "mog", "mol", "mom", "mon", "moo", "mop", "mor", "mos", "mot", "mow", "mud", "mug", "mum", "mun", "mus", "mut", "myc", "nab", "nae", "nag", "nah", "nam", "nan", "nap", "naw", "nay", "neb", "nee", "neg", "net", "new", "nib", "nil", "nim", "nip", "nit", "nix", "nob", "nod", "nog", "noh", "nom", "noo", "nor", "nos", "not", "now", "nth", "nub", "nun", "nus", "nut", "oaf", "oak", "oar", "oat", "oba", "obe", "obi", "oca", "oda", "odd", "ode", "ods", "oes", "off", "oft", "ohm", "oho", "ohs", "oil", "oka", "oke", "old", "ole", "oms", "one", "ono", "ons", "ooh", "oot", "ope", "ops", "opt", "ora", "orb", "orc", "ore", "ors", "ort", "ose", "oud", "our", "out", "ova", "owe", "owl", "own", "ox    o", "oxy", "pac", "pad", "pah", "pal", "pam", "pan", "pap", "par", "pas", "pat", "paw", "pax", "pay", "pea", "pec", "ped", "pee", "peg", "peh", "pen", "pep", "per", "pes", "pet", "pew", "phi", "pht", "pia", "pic", "pie", "pig", "pin", "pip", "    pis", "pit", "piu", "pix", "ply", "pod", "poh", "poi", "pol", "pom", "poo", "pop", "pot", "pow", "pox", "pro", "pry", "psi", "pst", "pub", "pud", "pug", "pul", "pun", "pup", "pur", "pus", "put", "pya", "pye", "pyx", "qat", "qis", "qua", "rad", "rag", "rah", "rai", "raj", "ram", "ran", "rap", "ras", "rat", "raw", "rax", "ray", "reb", "rec", "red", "ree", "ref", "reg", "rei", "rem", "rep", "res", "ret", "rev", "rex", "rho", "ria", "rib", "rid", "rif", "rig", "rim", "rin", "rip", "ro    b", "roc", "rod", "roe", "rom", "rot", "row", "rub", "rue", "rug", "rum", "run", "rut", "rya", "rye", "sab", "sac", "sad", "sae", "sag", "sal", "sap", "sat", "sau", "saw", "sax", "say", "sea", "sec", "see", "seg", "sei", "sel", "sen", "ser", "    set", "sew", "sex", "sha", "she", "shh", "shy", "sib", "sic", "sim", "sin", "sip", "sir", "sis", "sit", "six", "ska", "ski", "sky", "sly", "sob", "sod", "sol", "som", "son", "sop", "sos", "sot", "sou", "sow", "sox", "soy", "spa", "spy", "sri", "sty", "sub", "sue", "suk", "sum", "sun", "sup", "suq", "syn", "tab", "tad", "tae", "tag", "taj", "tam", "tan", "tao", "tap", "tar", "tas", "tat", "tau", "tav", "taw", "tax", "tea", "ted", "tee", "teg", "tel", "ten", "tet", "tew", "the", "th    o", "thy", "tic", "tie", "til", "tin", "tip", "tis", "tit", "tod", "toe", "tog", "tom", "ton", "too", "top", "tor", "tot", "tow", "toy", "try", "tsk", "tub", "tug", "tui", "tun", "tup", "tut", "tux", "twa", "two", "tye", "udo", "ugh", "uke", "    ulu", "umm", "ump", "uns", "upo", "ups", "urb", "urd", "urn", "urp", "use", "uta", "ute", "uts", "vac", "van", "var", "vas", "vat", "vau", "vav", "vaw", "vee", "veg", "vet", "vex", "via", "vid", "vie", "vig", "vim", "vis", "voe", "vow", "vox", "vug", "vum", "wab", "wad", "wae", "wag", "wan", "wap", "war", "was", "wat", "waw", "wax", "way", "web", "wed", "wee", "wen", "wet", "wha", "who", "why", "wig", "win", "wis", "wit", "wiz", "woe", "wog", "wok", "won", "woo", "wop", "wos", "wo    t", "wow", "wry", "wud", "wye", "wyn", "xis", "yag", "yah", "yak", "yam", "yap", "yar", "yaw", "yay", "yea", "yeh", "yen", "yep", "yes", "yet", "yew", "yid", "yin", "yip", "yob", "yod", "yok", "yom", "yon", "you", "yow", "yuk", "yum", "yup", "    zag", "zap", "zas", "zax", "zed", "zee", "zek", "zep", "zig", "zin", "zip", "zit", "zoa", "zoo", "zuz", "zzz", "END_OF_LIST"};

    public ArrayList<String> usernames = new ArrayList();
    public ArrayList<String> secrets = new ArrayList();
    public ArrayList<String> tokens = new ArrayList();
    public ArrayList<String> companies = new ArrayList();
    UpdateTask task = new UpdateTask(this);

    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    private GoogleApiClient client;
    protected String time;
    protected int time_off;
    final CustomAdapter customAdapter = new CustomAdapter();
    boolean removing = false;

    protected String convTOTP(int totp) {
        if (mode == 0) {
            return convTOTP_2(totp);
        }
        else if (mode == 1) {
            return convTOTP_3(totp);
        }
        else {
            String val = "" + totp;
            while (val.length() < 6) {
                val = "0" + val;
            }

            return val.substring(0,3) + " " + val.substring(3);
        }
    }
    protected String convTOTP_2(int totp) {
        int index1 = totp / 10000;
        int index2 = (totp / 100) % 100;
        int index3 = totp % 100;
        String w1 = "" + CONSONANTS[index1 / 5] + VOWELS[index1 % 5];
        String w2 = "" + CONSONANTS[index2 / 5] + VOWELS[index2 % 5];
        String w3 = "" + CONSONANTS[index3 / 5] + VOWELS[index3 % 5];
        return w1.toUpperCase() + " " + w2.toUpperCase() + " " + w3.toUpperCase();

    }

    protected String convTOTP_3(int totp) {
        int index1 = totp / 1000;
        int index2 = totp % 1000;
        String w1 = WORDLIST[index1];
        String w2 = WORDLIST[index2];
        String w1_fixed = "";
        String w2_fixed = "";
        for (int i = 0; i < w1.length(); i++) {
            if (w1.charAt(i) != ' ') {
                w1_fixed += w1.charAt(i);
            }
        }
        for (int i = 0; i < w2.length(); i++) {
            if (w2.charAt(i) != ' ') {
                w2_fixed += w2.charAt(i);
            }
        }
        return w1_fixed.toUpperCase() + " " + w2_fixed.toUpperCase();
    }
    public void pause() {
        //task.cancel(true);
        //task = new UpdateTask(this);
        try{executeMore();}catch(Exception e){System.out.println(e.getMessage());pause();}
    }
    public void asyncComplete(boolean success) {
        Log.e("NOTIFY","NOTIFYING :)");
        //task.cancel(true);
        //task = new UpdateTask(this);
        customAdapter.notifyDataSetChanged();
        try{execute();}catch(Exception e){Log.e("ERR",e.getMessage());}
        //try {Thread.sleep(10000);}catch(Exception e){};

            //task = new UpdateTask(this);
            //task.execute();
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_main, menu);
        return true;
    }
    protected void default_vals() {
        usernames.add("Bruce Wayne");
        usernames.add("Peter Parker");
        usernames.add("Tony Stark");
        usernames.add("Clark Kent");
        companies.add("Wayne Enterprises");
        companies.add("The Daily Bugle");
        companies.add("Stark Enterprises");
        companies.add("The Daily Planet");
        secrets.add(seed32);
        secrets.add(seed32_2);
        secrets.add(seed32_3);
        secrets.add(seed32_4);
    }
    protected void updateTokens() {
        Log.e("UPDATE","UPDATING :)");
        for (int i = 0; i < secrets.size(); i++) {
            tokens.add("");
            tokens.set(i,getToken(secrets.get(i)));
        }
    }
    protected String getToken(String secret) {
        time = "" + (System.currentTimeMillis() / 1000 / 30);
        time_off =(int)((System.currentTimeMillis() / 1000) % 30);
        return convTOTP((Integer.parseInt(generateTOTP(secret,time,"8","HmacSHA256")) % 1000000));
    }
    public void executeMore() throws InterruptedException {
        Log.e("EX","EXECUTED :)");
        task.cancel(true);
        Log.e("EX","EXECUTED2 :)");
        Thread.sleep(10);
        Log.e("EX","EXECUTED3 :)");

        task = new UpdateTask(this);
        task.execute();
        Log.e("EX","EXECUTED :)");
        //task = new UpdateTask(this);
        //task.execute();

    }
    protected void execute() throws InterruptedException {
        final Handler handler = new Handler();
        handler.postDelayed( new Runnable() {

            @Override
            public void run() {
                updateTokens();
                customAdapter.notifyDataSetChanged();
                handler.postDelayed( this, 1 * 1000 );
            }
        }, 1000 );
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //For Testing Purposes
        default_vals();
        updateTokens();
        setContentView(R.layout.activity_main);
        ListView listView = (ListView)findViewById(R.id.auth_list);
        listView.setAdapter(customAdapter);

        /*
        try {
        Bitmap myQRCode = BitmapFactory.decodeResource(getResources(),R.drawable.qr
        );}catch(Exception e){e.printStackTrace();}
        */
    
        client = new GoogleApiClient.Builder(this).addApi(AppIndex.API).build();
        try{execute();}catch(Exception e){e.printStackTrace();}

        /*
        new Thread(new Runnable(){
            public void run() {
                while (true) {

                    try {
                        Thread.sleep(1000);
                    } catch (Exception e) {
                    }
                }
            }
        }).start();
        */

        //int secret = Integer.parseInt(generateTOTP(seed32, time, "8", "HmacSHA256")) % 1000000;
        //Toast toast = Toast.makeText(getApplicationContext(), "" + secret, Toast.LENGTH_SHORT);
        //Log.e("VAL", "" + secret);
        //Log.e("STR2", convTOTP_2(secret));
        //Log.e("STR3", convTOTP_3(secret));
        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
    }
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (requestCode == 1) {
            if(resultCode == Activity.RESULT_OK){
                String[] result=data.getStringArrayExtra("result");
                if (result[2].length() > 0) {
                    usernames.add(result[0]);
                    companies.add(result[1]);
                    secrets.add(result[2]);
                    customAdapter.notifyDataSetChanged();
                }
            }
            if (resultCode == Activity.RESULT_CANCELED) {
                //Write your code if there's no result
            }
        }
    }//onActivityResult
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch(item.getItemId()) {
            case R.id.action_add:
                Log.e("ADD","CLICKED");
                Intent i = new Intent(this,InputActivity.class);
                startActivityForResult(i,1);
                return true;
            case R.id.action_remove:
                Log.e("REMOVE","CLICKED");
                if (removing) {
                    removing = false;
                    customAdapter.notifyDataSetChanged();
                }
                else {
                    removing = true;
                    customAdapter.notifyDataSetChanged();
                }

                return true;
            case R.id.action_switch:
                Log.e("SWITCH","CLICKED");
                mode = (mode + 1) % 3;
                return true;
            case R.id.action_qr:
                Log.e("QR","CLICKED");
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    protected byte[] hmac_sha(String crypto, byte[] keyBytes,
                              byte[] text) {
        try {
            Mac hmac;
            hmac = Mac.getInstance(crypto);
            SecretKeySpec macKey =
                    new SecretKeySpec(keyBytes, "RAW");
            hmac.init(macKey);
            return hmac.doFinal(text);
        } catch (GeneralSecurityException gse) {
            throw new UndeclaredThrowableException(gse);
        }
    }

    protected byte[] hexStr2Bytes(String hex) {
        // Adding one byte to get the right conversion
        // Values starting with "0" can be converted
        byte[] bArray = new BigInteger("10" + hex, 16).toByteArray();

        // Copy all the REAL bytes, not the "first"
        byte[] ret = new byte[bArray.length - 1];
        for (int i = 0; i < ret.length; i++)
            ret[i] = bArray[i + 1];
        return ret;
    }

    private static final int[] DIGITS_POWER
            // 0 1  2   3    4     5      6       7        8
            = {1, 10, 100, 1000, 10000, 100000, 1000000, 10000000, 100000000};

    public String generateTOTP256(String key,
                                  String time,
                                  String returnDigits) {
        return generateTOTP(key, time, returnDigits, "HmacSHA256");
    }

    private void updateView(int index, ListView listView){
        View v = listView.getChildAt(index);
        Log.e("FIRST","CALLED");
        if(v == null)
            return;
        Log.e("SECOND","CALLED");

        TextView someText = (TextView) v.findViewById(R.id.token);
        someText.setText("UPDATED");
    }
    public String generateTOTP(String key,
                               String time,
                               String returnDigits,
                               String crypto) {
        int codeDigits = Integer.decode(returnDigits).intValue();
        String result = null;

        // Using the counter
        // First 8 bytes are for the movingFactor
        // Compliant with base RFC 4226 (HOTP)
        while (time.length() < 16)
            time = "0" + time;

        // Get the HEX in a Byte[]
        byte[] msg = hexStr2Bytes(time);
        byte[] k = hexStr2Bytes(key);
        byte[] hash = hmac_sha(crypto, k, msg);

        // put selected bytes into result int
        int offset = hash[hash.length - 1] & 0xf;

        int binary =
                ((hash[offset] & 0x7f) << 24) |
                        ((hash[offset + 1] & 0xff) << 16) |
                        ((hash[offset + 2] & 0xff) << 8) |
                        (hash[offset + 3] & 0xff);

        int otp = binary % DIGITS_POWER[codeDigits];

        result = Integer.toString(otp);
        while (result.length() < codeDigits) {
            result = "0" + result;
        }
        return result;
    }

    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    public Action getIndexApiAction() {
        Thing object = new Thing.Builder()
                .setName("Main Page") // TODO: Define a title for the content shown.
                // TODO: Make sure this auto-generated URL is correct.
                .setUrl(Uri.parse("http://[ENTER-YOUR-URL-HERE]"))
                .build();
        return new Action.Builder(Action.TYPE_VIEW)
                .setObject(object)
                .setActionStatus(Action.STATUS_TYPE_COMPLETED)
                .build();
    }

    @Override
    public void onStart() {
        super.onStart();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client.connect();
        AppIndex.AppIndexApi.start(client, getIndexApiAction());
    }

    @Override
    public void onStop() {
        super.onStop();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        AppIndex.AppIndexApi.end(client, getIndexApiAction());
        client.disconnect();
    }
    class UpdateTask extends AsyncTask<Void,Void,Void> {
        private AsyncDelegate delegate;
        public UpdateTask(AsyncDelegate delegate) {
            this.delegate = delegate;
        }

        protected Void doInBackground(Void ... voids) {
                    updateTokens();
                    delegate.asyncComplete(true);
                 return null;
        }
        protected void onPostExecute(String result) {
            //delegate.asyncComplete(true);
        }

    }

    class CustomAdapter extends BaseAdapter {
        @Override
        public int getCount() {
            return secrets.size();
        }
        public Object getItem(int i) {
            return null;
        }
        public long getItemId(int i) {
            return 0;
        }
        public void remove(int view_num) {
            usernames.remove(view_num);
            tokens.remove(view_num);
            secrets.remove(view_num);
            companies.remove(view_num);
        }
        public View getView(int i, View view, ViewGroup viewGroup) {
            view = getLayoutInflater().inflate(R.layout.customlayout,null);
            final int view_num = i;

            TextView username = (TextView)view.findViewById(R.id.username);
            TextView token = (TextView)view.findViewById(R.id.token);
            TextView company = (TextView)view.findViewById(R.id.company);
            ImageView delete = (ImageView)view.findViewById(R.id.delete);
            if (removing) {
                delete.setVisibility(View.VISIBLE);
            }
            else {
                delete.setVisibility(View.INVISIBLE);
            }
            ProgressBar timer = (ProgressBar)view.findViewById(R.id.timer);
            if (i < usernames.size())
                username.setText(usernames.get(i));
            if (i < tokens.size()) {
                token.setText(tokens.get(i));
                if (time_off > 25)
                    token.setTextColor(Color.RED);
                else
                    token.setTextColor(Color.BLUE);
            }
            if (i < companies.size())
                company.setText(companies.get(i));
            timer.setProgress(500);
                view.setOnClickListener(new View.OnClickListener() {
                    @Override

                    public void onClick(View view) {
                        if (removing) {
                            remove(view_num);
                        }
                    }
                });


            return view;
        }

    }
}

