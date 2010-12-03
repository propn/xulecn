/*
 * Created on 2005-4-14
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.powerise.ibss.util;

import java.security.Key;

import javax.crypto.Cipher;

/**
 *
 * <p>Title: </p>
 *
 * <p>Description: ����Ա������ʵ����</p>
 *
 * <p>Copyright: Copyright (c) 2007</p>
 *
 * <p>Company: </p>
 *
 * @author not attributable
 * @version 1.0
 */
public class TrippleDesForParty{
    //TYPE
    private final static boolean ENCRYPT = false;
    private final static boolean DECRYPT = true;

    //MODE
    private final static int ECB = 1;
    private final static int CBC = 0;

    private final static int DES_BLOCKSIZE = 8;

    //�ܳ�
    private static final byte keyData[] = {(byte) 0xF4, (byte) 0x88, (byte) 0xFD, (byte) 0x58, (byte) 0x4E, (byte) 0x49,
                                  (byte) 0xDB, (byte) 0xCD, (byte) 0x20, (byte) 0xB4, (byte) 0x9D, (byte) 0xE4,
                                  (byte) 0x91, (byte) 0x07, (byte) 0x36, (byte) 0x6B, (byte) 0x33, (byte) 0x6C,
                                  (byte) 0x38, (byte) 0x0D, (byte) 0x45, (byte) 0x1D, (byte) 0x0F, (byte) 0x7C};

    /**
     * ����Ա������-- ԭ��ΪԱ����+����
     * @param partyRoleId int
     * @param password String
     * @return String
     */
    public static String encryptForPartyRole(int partyRoleId,String password) {
    	
        return encrypt(partyRoleId + password);
    }

    /**
     * �����ַ���
     * @param str
     * @return
     */
    public static String encrypt(String str) {
        byte[] result = null;
        try {
            result = runDES(ENCRYPT, ECB, keyData, str.getBytes(), str.length());
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
        return  parseByte2String(result);
    }

    /**
     * �����ַ���  --����������
     * @param str
     * @return
     */
//    public String decrypt(String str) {
//        byte[] result = null;
//        try {
//            result = runDES(DECRYPT, ECB, keyData, str.getBytes(), str.length());
//        } catch (Exception ex) {
//            ex.printStackTrace();
//            return null;
//        }
//        return this.parseByte2String(result);
//    }

    /**
     * ���ֽ���ת��Ϊ�ַ���
     * @param result byte[]
     * @return String
     */
    private static String parseByte2String(byte[] result) {
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < result.length; i++) {
            int k = result[i];
            if (k < 0) {
                k += 256;
            }
            String s = Integer.toHexString(k).toUpperCase();
            if (s.length() < 2) {
                s = "0" + s;
            }
            sb.append(s);
        }
        return sb.toString();
    }


    //All DES Mode&Type
    //Notice : The In.length must be divided by eight.or you must Pad the data first.
    //You can chose 1 key(DES) or 2 key,3 key (Triple DES) by Key.length
    //if you want to do a DES , input a Key with byte[8]
    //if you want to do a TripleDes with 2 Key, input a Key with byte[16]
    //if you want to do a TripleDes with 3 Key, input a Key with byte[24]
    private static byte[] runDES(boolean Type, int Mode, byte[] Key, byte[] inBuf, int iBufLen) throws Exception {
        //Judge the Input
        if (Mode != ECB && Mode != CBC) {
            throw new Exception("err Mode!");
        }
        if (Key.length != 8 && Key.length != 16 && Key.length != 24) {
            throw new Exception("err Key Length!");
        }
        //Get Key Nums
        int KeyNum = Key.length / 8;
        if (KeyNum <= 0 || KeyNum > 3) {
            throw new Exception("err Key Num!");
        }

        //Make the Sub Keys
        byte[][] SubKeys = new byte[KeyNum][];
        for (int i = 0; i < KeyNum; i++) {
            SubKeys[i] = new byte[8];
            System.arraycopy(Key, i * 8, SubKeys[i], 0, 8);
        }

        int blockCount = iBufLen / DES_BLOCKSIZE;
        int tail = iBufLen % DES_BLOCKSIZE;
        byte[] iBufTemp = new byte[(blockCount + 1) * DES_BLOCKSIZE];
        byte FillItem = (byte) (DES_BLOCKSIZE - tail);
        System.arraycopy(inBuf, 0, iBufTemp, 0, iBufLen);

        for (int i = 0; i < 8 - tail; i++) {
            iBufTemp[iBufLen + i] = FillItem;
        }

        int outStrLen = (blockCount + 1) * DES_BLOCKSIZE;
        //The Out Data, Length same to Input
        byte[] Out = new byte[outStrLen];

        if (Mode == ECB) {
            //The 8 bytes For Single DES
            byte[] Data = new byte[8];
            for (int i = 0; i < iBufTemp.length; i += 8) {
                System.arraycopy(iBufTemp, i, Data, 0, 8);
                Data = SingleDES(Type, SubKeys[Type ? 2 : 0], Data);
                Data = SingleDES(!Type, SubKeys[1], Data);
                Data = SingleDES(Type, SubKeys[Type ? 0 : 2], Data);
                System.arraycopy(Data, 0, Out, i, 8);
            }
        } else {
//            //The 8 bytes For Single DES
//            byte[] Data = new byte[8];
//            //CBC MODE
//            byte[] Vec = {(byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00,
//                         (byte) 0x00};
//            byte[] Vin = {(byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00,
//                         (byte) 0x00};
//
//            for (int i = 0; i < iBufLen; i += 8) {
//                System.arraycopy(In, i, Data, 0, 8);
//                //XOR The Input Data
//                if (Type == ENCRYPT) {
//                    for (int j = 0; j < 8; j++) {
//                        Vin[j] = (byte) (Data[j] ^ Vec[j]);
//                    }
//                } else {
//                    System.arraycopy(Data, 0, Vin, 0, 8);
//                }
//
//                //Run DES
//                Data = SingleDES(Type, SubKeys[Type ? 2 : 0], Vin);
//                Data = SingleDES(!Type, SubKeys[1], Data);
//                Data = SingleDES(Type, SubKeys[Type ? 0 : 2], Data);
//
//                if (Type == ENCRYPT) {
//                    //Copy Out Data to Vec
//                    System.arraycopy(Data, 0, Vec, 0, 8);
//                } else {
//                    for (int j = 0; j < 8; j++) {
//                        Data[j] ^= Vec[j];
//                    }
//                    //Copy Input Data to Vec
//                    System.arraycopy(Vin, 0, Vec, 0, 8);
//                }
//
//                //Copy The Out Data
//                System.arraycopy(Data, 0, Out, i, 8);
//            }
        }
        return Out;
    }


    //Single DES only for private use
    //if you want do a Single DES please use RunDES with a 8 byte Key
    protected static byte[] SingleDES(boolean Type, byte[] Key, byte[] In) throws Exception {
//        Security.addProvider(new com.sun.crypto.provider.SunJCE());
        if (Key.length != 8) {
            throw new Exception("err Key Length!");
        }

        Key key = new javax.crypto.spec.SecretKeySpec(Key, "DES");
        Cipher encryptCipher = Cipher.getInstance("DES/ECB/noPadding");

        if (Type == ENCRYPT) { //ENCRYPT
            encryptCipher.init(Cipher.ENCRYPT_MODE, key);
        } else { //DECRYPT
            encryptCipher.init(Cipher.DECRYPT_MODE, key);
        }
        return encryptCipher.doFinal(In);
    }


    public static void main(String[] args) {
        try {
            TrippleDesForParty des = new TrippleDesForParty();
            int partyRoleId = 100003;
            String passwd = "pass";
            String cryptedData = des.encryptForPartyRole(partyRoleId,passwd);
            System.out.println(cryptedData);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

}
