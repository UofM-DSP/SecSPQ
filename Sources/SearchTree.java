package snpLab.UofM;

import GenericTree.GenericTreeNode;
import HomomorphicEncryption.Paillier;
import flexsc.Flag;
import util.ConfigParser;
import util.EvaRunnable;

import java.math.BigInteger;
import java.util.*;

//import org.apache.commons.cli.ParseException;

/**
 * Created by zahidul on 7/28/16.
 */
public class SearchTree {
    static List<String> result = new ArrayList<String>();
    public static int k = 0;
    public static String query = "";
    public static String strKey = "";

    //static boolean isLeafNode = false;
//    static int [] snpIndex;
//    static int [] snpValue;

    //List<String> similarSequence()
//    public BigInteger countNumberOfSnps(Paillier paillier, List columnNames, GenericTree.GenericTreeNode rootNode, String query) throws ParseException, ClassNotFoundException, InstantiationException, IllegalAccessException {
    public BigInteger countNumberOfSnps(Paillier paillier, List columnNames, GenericTree.GenericTreeNode rootNode, String[] snpIdArray) throws ClassNotFoundException, InstantiationException, IllegalAccessException {
        BigInteger count = BigInteger.valueOf(0);

            GenericTree.GenericTree gTree = new GenericTree.GenericTree();

            //count = gTree.executeQueryOnTree(paillier, rootNode, snpIdArray);

        return count;
    }

    /*public void processQuery(String query, int k) throws ParseException, ClassNotFoundException, InstantiationException, IllegalAccessException{
        for (int i = 0; i < query.length(); i++) {
            initiiateEvaluatorEq(Character.toString(query.charAt(i)));
        }
        //to check whether the distance is less than or equal to k or not
        //if (isLeafNode == true)
        initiiateEvaluatorLeq(Integer.toString(k));

        if(isLeafNode == false){

        }
    }*/


    public void initiiateEvaluatorEq(String query)throws ClassNotFoundException, InstantiationException, IllegalAccessException {

        ConfigParser config = new ConfigParser("ConfigCircuit.conf");

        String[] args = new String[2];
        args[0] = "example.Millionaire";

        /*String seqY = "";

        if (query.equalsIgnoreCase("A"))
            seqY = "00";
        else if (query.equalsIgnoreCase("C"))
            seqY = "01";
        else if(query.equalsIgnoreCase("G"))
            seqY = "10";
        else seqY = "11";*/

        args[1] = query;

//        System.out.println("-----------SearchTree-----------args[1] : " + args[1]);

        Class<?> clazz = Class.forName(args[0]+"$Evaluator");
        final EvaRunnable run = (EvaRunnable) clazz.newInstance();
        run.setParameter(config, Arrays.copyOfRange(args, 1, args.length));

        Thread thread2 = new Thread () {
            public void run () {
                run.run();
            }
        };
        if(Flag.CountTime)
            Flag.sw.print();
        if(Flag.countIO)
            run.printStatistic();

        thread2.start();
        try {
            thread2.join();
        }catch(InterruptedException ie)
        {
            //Log message if required.
        }
    }

    public void initiiateEvaluatorDecryptVal (String encryptedVal)throws ClassNotFoundException, InstantiationException, IllegalAccessException {

        ConfigParser config = new ConfigParser("ConfigCircuit.conf");

        String[] args = new String[2];
        args[0] = "example.AESDecryption";
        args[1] = encryptedVal;

//        System.out.println("-----------SearchTree-----------args[1] : " + args[1]);

        Class<?> clazz = Class.forName(args[0]+"$Evaluator");
        final EvaRunnable run = (EvaRunnable) clazz.newInstance();
        run.setParameter(config, Arrays.copyOfRange(args, 1, args.length));

        Thread thread2 = new Thread () {
            public void run () {
                run.run();
            }
        };
        if(Flag.CountTime)
            Flag.sw.print();
        if(Flag.countIO)
            run.printStatistic();

        thread2.start();
        try {
            thread2.join();
        }catch(InterruptedException ie)
        {
            //Log message if required.
        }
    }

    public void initiiateEvaluatorDecryptValDist (String encryptedVal)throws ClassNotFoundException, InstantiationException, IllegalAccessException {

        ConfigParser config = new ConfigParser("ConfigCircuit.conf");

        String[] args = new String[2];
        args[0] = "example.AESDecryptionDist";
        args[1] = encryptedVal;

//        System.out.println("-----------SearchTree-----------args[1] : " + args[1]);

        Class<?> clazz = Class.forName(args[0]+"$Evaluator");
        final EvaRunnable run = (EvaRunnable) clazz.newInstance();
        run.setParameter(config, Arrays.copyOfRange(args, 1, args.length));

        Thread thread2 = new Thread () {
            public void run () {
                run.run();
            }
        };
        if(Flag.CountTime)
            Flag.sw.print();
        if(Flag.countIO)
            run.printStatistic();

        thread2.start();
        try {
            thread2.join();
        }catch(InterruptedException ie)
        {
            //Log message if required.
        }
    }

    public List<String> hammingDistance(GenericTree.GenericTree gt, GenericTree.GenericTreeNode rootNode, String query, int k) throws  ClassNotFoundException, InstantiationException, IllegalAccessException{
        //getHammingDistanceSeq(gt, rootNode, query, k, rootNode);
        Map snpIdMap = mapSnpToId();
        String encodedQuery = "";
        for (int i = 0; i < query.length(); i++) {
            String currentQueryChar = Character.toString(query.charAt(i));
            encodedQuery += snpIdMap.get(currentQueryChar);
        }
        getHammingDistanceSeqWithBigInteger(gt, rootNode, encodedQuery, k, rootNode);
        return result;
    }

    public List<String> hammingDistance_(GenericTree.GenericTree gt, GenericTree.GenericTreeNode rootNode) throws ClassNotFoundException, InstantiationException, IllegalAccessException{
        //getHammingDistanceSeq(gt, rootNode, query, k, rootNode);
        Map<String, String> snpIdMap = mapSnpToId();
        String encodedQuery = "";
        for (int i = 0; i < query.length(); i++) {
//            String currentQueryChar = Character.toString(query.charAt(i));
            encodedQuery += snpIdMap.get(""+query.charAt(i));
        }
        getHammingDistanceSeqWithBigInteger(gt, rootNode, encodedQuery, k, rootNode);
        return result;
    }

    public static void getHammingDistanceSeqWithBigInteger(GenericTree.GenericTree gt, GenericTree.GenericTreeNode rootNode, String query_, int k, GenericTree.GenericTreeNode orgRootNode) throws ClassNotFoundException, InstantiationException, IllegalAccessException {
        int numberOfChild = rootNode.getNumberOfChildren();
        for (int i=0;i<numberOfChild;i++) {
            GenericTreeNode currentNode = rootNode.getChildAt(i);
            if (currentNode.checkLeafNode() == true) {
//                BigInteger currentNodeVal = currentNode.getData();
                String currentNodeVal = currentNode.getData();
//                BigInteger keyStream = currentNode.getKeystream();
                CheckEquality eq = new CheckEquality();
                Boolean flag = eq.decryptInGC(strKey, query_, Integer.toString(k), currentNodeVal);
//                int dist = eq.decryptInGCDist(strKey, query_, currentNodeVal);
//                dist = getSecureHammingDistance(query_, decryptedValue.toString());
                //System.out.println("in leaf node --- distance: " + dist);
//                if (flag == true) {
                int dist = eq.decryptInGCDist(strKey, query_, currentNodeVal);
                if (dist <= k) {
                    //we got the result, need to concatenate the sequences up to the root
                    String tempRes = currentNode.getData();
                    StringBuilder encryptedSNPsequence = new StringBuilder();
                    for(int x = 0; x < strKey.length(); x++)
                        encryptedSNPsequence.append((char)(strKey.charAt(x) ^ tempRes.charAt(x % tempRes.length())));
//                    String orgVal1 = encryptedSNPsequence.toString();
                    String orgVal = encryptedSNPsequence.toString().substring(0,currentNode.getDataLength());
//                    String orgVal = "" + strKey.xor(new BigInteger(tempRes));
                    GenericTreeNode tempNode = currentNode;
                    String[] ids = currentNode.getIds().split(",");
                    while(tempNode.getParent() != orgRootNode){
                        tempNode = tempNode.getParent();
                        //tempRes = tempNode.getData().toString() + tempRes;
                        tempRes = tempNode.getData();
                        StringBuilder encryptedSNPsequenceNew = new StringBuilder();
                        for(int x = 0; x < strKey.length(); x++)
                            encryptedSNPsequenceNew.append((char)(strKey.charAt(x) ^ tempRes.charAt(x % tempRes.length())));
//                        String orgval1 = encryptedSNPsequenceNew.toString();
//                        String orgval2 = encryptedSNPsequenceNew.toString().substring(0,tempNode.getDataLength());
                        orgVal = encryptedSNPsequenceNew.toString().substring(0,tempNode.getDataLength()) + orgVal;
                    }
                    String final_ = orgVal;
                    for (String id : ids) {
                        if (!id.isEmpty() && !id.equals("null")) {
                            serverJson.result1.put(Integer.parseInt(id), orgVal);
                            serverJson.resultScores.put(Integer.parseInt(id), k);
                        }
                    }
                    //got the string, now same sequence can be multiple times, we can check this by seq id
                    String[] total_seq = currentNode.getIds().split(",");
                    for (int j = 1; j < total_seq.length; j++){
                        String plaintext = "";
                        //result.add(tempRes);
                        Map<String, String> snpIdMapR = mapSnpToIdR();
                        for (int l = 0; l < orgVal.length(); l=l+3) {
                            plaintext += snpIdMapR.get(orgVal.substring(l,l+3));
                            //System.out.println(plaintext);
                        }
                        //System.out.println(plaintext);
                        result.add(plaintext);
                    }
                }

            } else {

                String currentNodeVal = currentNode.getData();
//                BigInteger keyStream = currentNode.getKeystream();
                CheckEquality eq = new CheckEquality();
//                StringBuilder encryptedSNPsequence = new StringBuilder();
//                for(int x = 0; x < strKey.length(); x++)
//                    encryptedSNPsequence.append((char)(strKey.charAt(x) ^ currentNodeVal.charAt(x % currentNodeVal.length())));
//                String orgVal = encryptedSNPsequence.toString();
//                BigInteger decryptedValue = eq.decryptInGC(currentNodeVal,strKey);
                int data_length = currentNode.getDataLength();
                String subQuery = query_.substring(0,data_length);
//                int dist = eq.decryptInGC(strKey, subQuery, Integer.toString(k), currentNodeVal);
                int dist = eq.decryptInGCDist(strKey, subQuery, currentNodeVal);
//                dist = getSecureHammingDistance(subQuery, decryptedValue.toString());
                //System.out.println("distance: " + dist);
//                if (flag == true){
                if (dist <= k){
                    String updatedQuery = query_.substring(data_length);
//                    int updatedK = k;
                    int updatedK = k-dist;
                    getHammingDistanceSeqWithBigInteger(gt, currentNode, updatedQuery, updatedK, orgRootNode);
                }
            }
        }
    }

    public static int getHammingDistance(String sequenceX, String sequenceY) {
        int dist = 0;
        boolean eqCheck = false;
        if (sequenceX.length() != sequenceY.length()) {
            return -1; //input strings should be of equal length
        }

        for (int i = 0; i < sequenceX.length(); i++) {
            if (eqCheck == false) {
                dist++;
            }
        }
        return dist;
    }

    public Map mapSnpToId() {
        Map<String, String> snpIdMap = new HashMap<String, String>();
        snpIdMap.put("A", "000");
        snpIdMap.put("C", "011");
        snpIdMap.put("G", "101");
        snpIdMap.put("T", "110");
        return snpIdMap;
    }

    public static Map mapSnpToIdR() {
        Map<String, String> snpIdMap = new HashMap<String, String>();
        snpIdMap.put("000", "A");
        snpIdMap.put("011", "C");
        snpIdMap.put("101", "G");
        snpIdMap.put("110", "T");
        return snpIdMap;
    }
}
