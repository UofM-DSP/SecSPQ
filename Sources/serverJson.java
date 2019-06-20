package snpLab.UofM;/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


/**
 *
 * @author mahdi
 */
import AES_CTR.AESCTR;
import GenericTree.GenericTree;
import GenericTree.GenericTreeNode;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * A TCP server that runs on port 9090. When a client connects, it sends the
 * client the current date and time, then closes the connection with that
 * client. Arguably just about the simplest server you can write.
 */
public class serverJson {

    //static Paillier paillier;
    static AESCTR aesctr;
//    static String queryFromClient;
//    static int [] snpIndex;
//    static int [] snpValue;
    static String[] snpIdArray;
    static Map<Integer, String> result1 = new HashMap<>();
    static Map<Integer, Integer> resultScores = new HashMap<>();

    public void runServerSocket(int port) {
        try {
            ServerSocket ss = new ServerSocket(port);
            Socket s = ss.accept();
            InputStream is = s.getInputStream();
            ObjectInputStream ois = new ObjectInputStream(is);
            QueryObjectSerializable to = (QueryObjectSerializable) ois.readObject();
            snpIdArray = to.snpIdArray;
//            snpValue = to.snpValue;
//            if (to!=null){System.out.println(to.query);}

//            queryFromClient = to.query;
            ois.close();
            is.close();
            s.close();
            ss.close();
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    public static void main(String args[]) throws ClassNotFoundException, InstantiationException, IllegalAccessException, Exception {
        int port = 2002;
        List<String> seqList = new ArrayList();
        Map<Integer, Integer> indexmap = new HashMap<>();

        DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        Date date = new Date();

        BuildTree bt = new BuildTree();

        try {
            //Scanner fileScanner = new Scanner(new File("SyntheticDataFull.txt"));
//            Scanner fileScanner = new Scanner(new File("test_data2.txt"));
//            Scanner fileScanner = new Scanner(new File("db_2000.fa"));
//            Scanner fileScanner = new Scanner(new File("db.fa"));
            Scanner fileScanner = new Scanner(new File("db_h2k_w2000.txt"));
            int index = 0;
            while (fileScanner.hasNextLine()) {
//                String next = fileScanner.nextLine();
//                if (!next.contains(">")) {
////                    seqList.add(next);
//                    seqList.add(next.substring(0,3400));
//                }
                String s = fileScanner.nextLine();
                if (s.contains(">")) {
                    index = Integer.parseInt(s.replace(">", ""));
//                System.out.println("doing " + index);
                } else {
//                query = next;
                    seqList.add(s.substring(0,10));
//                    seqList.add(s.substring(0,1000));
                    indexmap.put(seqList.size(), index);
                }
            }
            fileScanner.close();

        } catch (Exception ex) {
            ex.printStackTrace();
        }
        Date date1 = new Date();
        System.out.println("Data fetching Time: " + (date1.getTime() - date.getTime()));

        date = new Date();
        GenericTree gt = bt.generateTree(seqList);
        date1 = new Date();
        System.out.println("Tree building Time: " + (date1.getTime() - date.getTime()));
//        System.out.println(PlaintextExperiments.ObjectSizeFetcher.getObjectSize(gt));

        date = new Date();

        GenericTreeNode rootNode = gt.getRoot();
        //aesctr = bt.encryptTree(gt);
        GenericTree encryptedTree = bt.encryptTree_(gt);

        date1 = new Date();
        System.out.println("Encryption Time: " + (date1.getTime() - date.getTime()));
//        System.out.println(PlaintextExperiments.ObjectSizeFetcher.getObjectSize(gt));
        //serverJson sj = new serverJson();
        //sj.runServerSocket(port);
        //String query = "TGCCTG";
        date = new Date();
        //String query = "AGGGTTATATTGAGTATGCCTTCATATCCCCTGTAAGAGTCGGACATGCTCCCCGGGGGAACACCGGGTGCTATCACTAATCsnpLab/UofM/serverJson.java:93TTGCATCGATGAAAATCGGGTTTGATTACAAGGAATTCTGCCTGGAGCTGGAAAGACGACCCTCGAGCGAGTATATAAGTTACGCTCGGAATGATAGACTCGAGTGTGTAAACGTCTAAGGTCAACCCCAGTAATATACATGTAGTAGTGGTTCAATTTCGCCGGTTACGGCCTAAAATTACTACGTACATAAGTGTCGTTTCTCGCCCTCTTACCTAATGAACGGAACAAACTTATAACCACAACTTGGTTGGCTGCCGGTTACATTTTTAAAGCCATTATCCGTGTATTAGAGCTCGCAGATCTCATTGGTTTAAACATACAGGCACACTATCTCGATGAAGTTTCGGGGCATGACGATCTGATACATACTTTTAGTAGATAAGGTCTTGTATGGGGACTCCGACCAGCACAGTTTAACGACTAATCGCCCACCATCCTCCTAAATTCCACTGATAAAAATTAGAGCCTGCCGAGGAACTAGGTTCGGTGACTGGCCCCCGGAATTATAGCATAAGGACCGTGGACAAGGACTCGCACACCAGGGCGTGAGCAAGATAACTTTCCATCCCGAGCTGCATTGCGATTCTAGCACTTAGTCTTTGACCTAACGTTCTTTAGGCGTATTACTAATAAATCCTGCCAATGGTGACACTAGATGACGGGGAAATCTAGGCTGATAGACCAAATATGGATGCTTACCGAGAACGCGACTTGGAAGTTTAACCCTGTATTTCGCTGTCGTTCGGGCACACATAAGCGCGTCCCAATATCGGTTAGATAAAGTGGGCGTCCTGCAGGACACACGCTTTCATGTCCGTGTATTAAATGTGGCTTATTAGGCTACAACTAGGATGTCGAGGACGTTCCGACGGACTGTCGAGGTTGAACTCTGTATCCAGAATCTGAGAAATGGAT";
        //String query = "AGGGTTACTAGGGTTACAAC";
        //int k = 3;
        clientJson cj = new clientJson();
        cj.initilizaQueryandK();
        SearchTree st = new SearchTree();
        //List<String> r= st.hammingDistance(gt,rootNode,query,k);
        //List<String> result = st.hammingDistance(encryptedTree,rootNode,query,k);
        List<String> result = st.hammingDistance_(encryptedTree, rootNode);
//        List<String> result = st.hammingDistance_(gt, gt.getRoot());
        date1 = new Date();
        //List<String> r= st.hammingDistance_(gt,rootNode);
        //System.out.println(result);
        System.out.println("============================================================");
        System.out.println("Total result found: " + result.size());
        //System.out.println("Query: \n" + query+ "\n");
//        for (String element : result) {
//            System.out.println(element);
//        }
//        resultScores = util.Utils.sortByValueRev(resultScores);
//        for (Map.Entry<Integer, Integer> entrySet : resultScores.entrySet()) {
//            System.out.println((entrySet.getKey()) + " " + entrySet.getValue());
//        }

        int ind = 0, tmp = 99999;
        new File("query_results\\").mkdir();
        System.out.println("written query_results\\82.txt");
        PrintWriter pw = new PrintWriter(new BufferedWriter(new FileWriter("query_results\\82.txt", false)));

        for (Map.Entry<Integer, Integer> entrySet : resultScores.entrySet()) {
            if (tmp != entrySet.getValue()) {
                ind++;
            }
            //System.out.println(ind + ":" + entrySet.getKey() + " dist " + entrySet.getValue());//+" refdist "+refDistanceMap.get(entrySet.getKey())+
            pw.println(ind + ":" + indexmap.get(entrySet.getKey()) + "," + entrySet.getValue()/2);
            tmp = entrySet.getValue();
        }
        pw.flush();
        pw.close();
        System.out.println("============================================================");
        System.out.println("query execution Time: " + (date1.getTime() - date.getTime()));
        System.out.println("Total circuit used: " + CheckEquality.circuitCount + " and communication overhead: " + CheckEquality.circuitCount * 128 * 2);
    }
}
