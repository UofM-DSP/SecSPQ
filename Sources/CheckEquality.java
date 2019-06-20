package snpLab.UofM;

import HomomorphicEncryption.Paillier;
import flexsc.Flag;
import util.ConfigParser;
import util.GenRunnable;

import java.math.BigInteger;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

//import org.apache.commons.cli.ParseException;


/**
 * Created by zahidul on 8/1/16.
 */
public class CheckEquality {
    public static boolean output = false;
    public static Boolean decryptResult = false;
    public static int dist = 0;
    public static int addVal = 0;
    public static int subVal = 0;
    public static int circuitCount = 0;
    public static boolean outputLeq = false;
    public boolean equalityCheckViaCircuit(Paillier paillier, BigInteger genInput, int indexNumber) throws ClassNotFoundException, InstantiationException, IllegalAccessException {
        circuitCount++;
        Random rand = new Random();
        int n = rand.nextInt(1000000);
//        System.out.println("Noise : " + n);
//        System.out.println("decryptedValue: " + paillier.Decryption(genInput).toString());

        BigInteger noise = BigInteger.valueOf(n);
        BigInteger encryptedNoise = paillier.Encryption(noise);
        BigInteger valueWithNoise = paillier.add(genInput, encryptedNoise);


        ConfigParser config = new ConfigParser("ConfigCircuit.conf");
        String[] args = new String[2];
        args[0] = "example.Millionaire";
        args[1] = String.valueOf(n);//String.valueOf(526444);//

        //System.out.println("----------CheckEquality------------args[1] : " + args[1]);

        Class<?> clazz = Class.forName(args[0]+"$Generator");
        final GenRunnable run = (GenRunnable) clazz.newInstance();
        run.setParameter(config, Arrays.copyOfRange(args, 1, args.length));

        boolean q = false;
        Thread thread1 = new Thread () {
            public void run () {
               run.run();
            }
        };

        if(Flag.CountTime)
            Flag.sw.print();


        thread1.start();


        /*Socket s = null;
        try{
            s = new Socket("130.179.30.95", 2002);
            OutputStream os = s.getOutputStream();
            ObjectOutputStream oos = new ObjectOutputStream(os);


            ServerResponseSerializable srs = new ServerResponseSerializable(true, BigInteger.valueOf(0), indexNumber, valueWithNoise, paillier);
            oos.writeObject(srs);
            oos.close();
            os.close();
        }catch(Exception e){
            System.out.println(e);
        }finally
        {
            try
            {
                s.close();
            }
            catch(Exception e){}
        }
*/

        try {
            thread1.join();
        }catch(InterruptedException ie)
        {
            //Log message if required.
        }

        return output;

    }

    public Map mapSnpToId() {
        Map<String , String> snpIdMap = new HashMap<String, String>();
        snpIdMap.put("A", "00");
        snpIdMap.put("C", "01");
        snpIdMap.put("G", "10");
        snpIdMap.put("T", "11");
        return snpIdMap;
    }

    public boolean equalityCheckViaCircuitNew_ (String nodeData) throws  ClassNotFoundException, InstantiationException, IllegalAccessException {
        circuitCount++;
        ConfigParser config = new ConfigParser("ConfigCircuit.conf");
        String[] args = new String[2];
        args[0] = "example.Millionaire";
        //Map snpIdMap = mapSnpToId();
        String seqX = "";
        if (nodeData.equalsIgnoreCase("A"))
            seqX = "00";
        else if (nodeData.equalsIgnoreCase("C"))
            seqX = "01";
        else if(nodeData.equalsIgnoreCase("G"))
            seqX = "10";
        else seqX = "11";

        args[1] = seqX;

        //System.out.println("----------CheckEquality------------args[1] : " + args[1]);

        Class<?> clazz = Class.forName(args[0]+"$Generator");
        final GenRunnable run = (GenRunnable) clazz.newInstance();
        run.setParameter(config, Arrays.copyOfRange(args, 1, args.length));

        boolean q = false;
        Thread thread1 = new Thread () {
            public void run () {
                run.run();
            }
        };

        if(Flag.CountTime)
            Flag.sw.print();


        thread1.start();

        try {
            thread1.join();
        }catch(InterruptedException ie)
        {
            //Log message if required.
        }

        return output;

    }

    public boolean equalityCheckViaCircuitNew (String sequenceX, String sequenceY) throws ClassNotFoundException, InstantiationException, IllegalAccessException {
        circuitCount++;
        ConfigParser config = new ConfigParser("ConfigCircuit.conf");
        String[] args = new String[2];
        args[0] = "example.Millionaire";
        //Map snpIdMap = mapSnpToId();
        /*String seqX = "";
        if (sequenceX.equalsIgnoreCase("A"))
            seqX = "00";
        else if (sequenceX.equalsIgnoreCase("C"))
            seqX = "01";
        else if(sequenceX.equalsIgnoreCase("G"))
            seqX = "10";
        else seqX = "11";*/

        args[1] = sequenceX;

        //System.out.println("----------CheckEquality------------args[1] : " + args[1]);

        Class<?> clazz = Class.forName(args[0]+"$Generator");
        final GenRunnable run = (GenRunnable) clazz.newInstance();
        run.setParameter(config, Arrays.copyOfRange(args, 1, args.length));

        boolean q = false;
        Thread thread1 = new Thread () {
            public void run () {
                run.run();
            }
        };

        if(Flag.CountTime)
            Flag.sw.print();


        thread1.start();

        SearchTree st = new SearchTree();
        st.initiiateEvaluatorEq(sequenceY);

        try {
            thread1.join();
        }catch(InterruptedException ie)
        {
            //Log message if required.
        }

        return output;

    }

    public Boolean decryptInGC (String key, String query, String k, String nodeVal) throws  ClassNotFoundException, InstantiationException, IllegalAccessException {
        circuitCount++;
        ConfigParser config = new ConfigParser("ConfigCircuit.conf");
        String[] args = new String[4];
        args[0] = "example.AESDecryption";
        args[1] = key;
        args[2] = query;
        args[3] = k;

        //System.out.println("----------CheckEquality------------args[1] : " + args[1]);

        Class<?> clazz = Class.forName(args[0]+"$Generator");
        final GenRunnable run = (GenRunnable) clazz.newInstance();
        run.setParameter(config, Arrays.copyOfRange(args, 1, args.length));

        Thread thread1 = new Thread () {
            public void run () {
                run.run();
            }
        };

        if(Flag.CountTime)
            Flag.sw.print();


        thread1.start();

        SearchTree st = new SearchTree();
        st.initiiateEvaluatorDecryptVal(nodeVal.toString());

        try {
            thread1.join();
        }catch(InterruptedException ie)
        {
            //Log message if required.
        }

        return decryptResult;

    }

    public int decryptInGCDist (String key, String query, String nodeVal) throws ClassNotFoundException, InstantiationException, IllegalAccessException {
        circuitCount++;
        ConfigParser config = new ConfigParser("ConfigCircuit.conf");
        String[] args = new String[3];
        args[0] = "example.AESDecryptionDist";
        args[1] = key;
        args[2] = query;

        //System.out.println("----------CheckEquality------------args[1] : " + args[1]);

        Class<?> clazz = Class.forName(args[0]+"$Generator");
        final GenRunnable run = (GenRunnable) clazz.newInstance();
        run.setParameter(config, Arrays.copyOfRange(args, 1, args.length));

        Thread thread1 = new Thread () {
            public void run () {
                run.run();
            }
        };

        if(Flag.CountTime)
            Flag.sw.print();


        thread1.start();

        SearchTree st = new SearchTree();
        st.initiiateEvaluatorDecryptValDist(nodeVal.toString());

        try {
            thread1.join();
        }catch(InterruptedException ie)
        {
            //Log message if required.
        }

        return dist;

    }

    public boolean lessORequalityCheckViaCircuit (int hammingDist) throws ClassNotFoundException, InstantiationException, IllegalAccessException {
        circuitCount++;
        ConfigParser config = new ConfigParser("ConfigCircuit.conf");
        String[] args = new String[2];
        args[0] = "example.leqGC";
        args[1] = Integer.toString(hammingDist);

        //System.out.println("----------CheckEquality------------args[1] : " + args[1]);

        Class<?> clazz = Class.forName(args[0]+"$Generator");
        final GenRunnable run = (GenRunnable) clazz.newInstance();
        run.setParameter(config, Arrays.copyOfRange(args, 1, args.length));

        boolean q = false;
        Thread thread1 = new Thread () {
            public void run () {
                run.run();
            }
        };

        if(Flag.CountTime)
            Flag.sw.print();


        thread1.start();

        try {
            thread1.join();
        }catch(InterruptedException ie)
        {
            //Log message if required.
        }

        return outputLeq;

    }

}
