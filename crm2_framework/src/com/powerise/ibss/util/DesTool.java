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
 * @author Administrator
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class DesTool {
    private static final int ivsize=8;
    private static final int bsize=24;
    private javax.crypto.Cipher cipher;    

    
    //TYPE
    public final static boolean    ENCRYPT = false;
    public final static boolean    DECRYPT = true;

    //MODE
    public final static int        ECB     = 1;
    public final static int        CBC     = 0;

    //All DES Mode&Type
    //Notice : The In.length must be divided by eight.or you must Pad the data first.
    //You can chose 1 key(DES) or 2 key,3 key (Triple DES) by Key.length
    //if you want to do a DES , input a Key with byte[8]
    //if you want to do a TripleDes with 2 Key, input a Key with byte[16]
    //if you want to do a TripleDes with 3 Key, input a Key with byte[24]
    public static byte [] RunDES(boolean Type,int Mode,byte [] Key,byte [] In) throws Exception {
        //Judge the Input
        if(Mode != ECB && Mode != CBC) {
            throw new Exception("err Mode!");
        }
        if(Key.length != 8 && Key.length != 16 && Key.length != 24) {
            throw new Exception("err Key Length!");
        }
        if(In.length%8 != 0) {
            throw new Exception("err Data Lenghth!");
        }

        //Get Key Nums
        int KeyNum = Key.length/8;
        if(KeyNum <=0 || KeyNum > 3) {
            throw new Exception("err Key Num!");
        }

        //Make the Sub Keys
        byte [][] SubKeys = new byte[KeyNum][];
        for(int i=0;i<KeyNum;i++) {
            SubKeys[i] = new byte[8];
            System.arraycopy(Key,i*8,SubKeys[i],0,8);
        }

        //The Out Data, Length same to Input
        byte [] Out  = new byte[In.length];

        if(Mode == ECB) {
            //The 8 bytes For Single DES
            byte [] Data = new byte[8];

            switch(KeyNum) {
            case 1:
                for(int i=0;i<In.length;i+=8) {
                    System.arraycopy(In,i,Data,0,8);
                    Data = SingleDES(Type,SubKeys[0],Data);
                    System.arraycopy(Data,0,Out,i,8);
                }
                break;
            case 2:
                for(int i=0;i<In.length;i+=8) {
                    System.arraycopy(In,i,Data,0,8);
                    Data = SingleDES(Type,SubKeys[0],Data);
                    Data = SingleDES(!Type,SubKeys[1],Data);
                    Data = SingleDES(Type,SubKeys[0],Data);
                    System.arraycopy(Data,0,Out,i,8);
                }
                break;
            default:
                for(int i=0;i<In.length;i+=8) {
                    System.arraycopy(In,i,Data,0,8);
                    Data = SingleDES(Type,SubKeys[Type ? 2 : 0],Data);
                    Data = SingleDES(!Type,SubKeys[1],Data);
                    Data = SingleDES(Type,SubKeys[Type ? 0 : 2],Data);
                    System.arraycopy(Data,0,Out,i,8);
                }
                break;
            }
        }
        else {
            //The 8 bytes For Single DES
            byte [] Data = new byte[8];
            //CBC MODE
            byte [] Vec = {(byte)0x00,(byte)0x00,(byte)0x00,(byte)0x00,(byte)0x00,(byte)0x00,(byte)0x00,(byte)0x00};
            byte [] Vin = {(byte)0x00,(byte)0x00,(byte)0x00,(byte)0x00,(byte)0x00,(byte)0x00,(byte)0x00,(byte)0x00};

            switch(KeyNum) {
            case 1:
                for (int i = 0; i < In.length; i += 8) {
                    System.arraycopy(In, i, Data, 0, 8);
                    //XOR The Input Data
                    if (Type == ENCRYPT) {
                        for(int j=0;j<8;j++) {
                            Vin[j] = (byte)(Data[j] ^ Vec[j]);
                        }
                    }
                    else {
                        System.arraycopy(Data, 0, Vin, 0, 8);
                    }

                    //Run DES
                    Data = SingleDES(Type, SubKeys[0], Vin);
                    if (Type == ENCRYPT) {
                        //Copy Out Data to Vec
                        System.arraycopy(Data, 0, Vec, 0, 8);
                    }
                    else {
                        for(int j=0;j<8;j++) {
                            Data[j] ^= Vec[j];
                        }
                        //Copy Input Data to Vec
                        System.arraycopy(Vin, 0, Vec, 0, 8);
                    }

                    //Copy The Out Data
                    System.arraycopy(Data, 0, Out, i, 8);
                }
                break;
            case 2:
                for (int i = 0; i < In.length; i += 8) {
                    System.arraycopy(In, i, Data, 0, 8);
                    //XOR The Input Data
                    if (Type == ENCRYPT) {
                        for (int j = 0; j < 8; j++) {
                            Vin[j] = (byte) (Data[j] ^ Vec[j]);
                        }
                    } else {
                        System.arraycopy(Data, 0, Vin, 0, 8);
                    }

                    //Run DES
                    Data = SingleDES(Type, SubKeys[0], Vin);
                    Data = SingleDES(!Type, SubKeys[1], Data);
                    Data = SingleDES(Type, SubKeys[0], Data);

                    if (Type == ENCRYPT) {
                        //Copy Out Data to Vec
                        System.arraycopy(Data, 0, Vec, 0, 8);
                    } else {
                        for (int j = 0; j < 8; j++) {
                            Data[j] ^= Vec[j];
                        }
                        //Copy Input Data to Vec
                        System.arraycopy(Vin, 0, Vec, 0, 8);
                    }

                    //Copy The Out Data
                    System.arraycopy(Data, 0, Out, i, 8);
                }
                break;
            default:
                for (int i = 0; i < In.length; i += 8) {
                    System.arraycopy(In, i, Data, 0, 8);
                    //XOR The Input Data
                    if (Type == ENCRYPT) {
                        for (int j = 0; j < 8; j++) {
                            Vin[j] = (byte) (Data[j] ^ Vec[j]);
                        }
                    } else {
                        System.arraycopy(Data, 0, Vin, 0, 8);
                    }

                    //Run DES
                    Data = SingleDES(Type, SubKeys[Type ? 2 : 0], Vin);
                    Data = SingleDES(!Type, SubKeys[1], Data);
                    Data = SingleDES(Type, SubKeys[Type? 0 : 2], Data);

                    if (Type == ENCRYPT) {
                        //Copy Out Data to Vec
                        System.arraycopy(Data, 0, Vec, 0, 8);
                    } else {
                        for (int j = 0; j < 8; j++) {
                            Data[j] ^= Vec[j];
                        }
                        //Copy Input Data to Vec
                        System.arraycopy(Vin, 0, Vec, 0, 8);
                    }

                    //Copy The Out Data
                    System.arraycopy(Data, 0, Out, i, 8);
                }
                break;
            }
        }

        return Out;
    }



    //Single DES only for private use
    //if you want do a Single DES please use RunDES with a 8 byte Key
    protected static byte [] SingleDES(boolean Type,byte [] Key,byte [] In) throws Exception {
//        Security.addProvider(new com.sun.crypto.provider.SunJCE());
        if(Key.length != 8) {
            throw new Exception("err Key Length!");
        }

        Key key = new javax.crypto.spec.SecretKeySpec(Key, "DES");
        Cipher encryptCipher = Cipher.getInstance("DES/ECB/noPadding");


        if(Type == ENCRYPT) { //ENCRYPT
            encryptCipher.init(Cipher.ENCRYPT_MODE, key);
        }
        else {                //DECRYPT
            encryptCipher.init(Cipher.DECRYPT_MODE, key);
        }

        return encryptCipher.doFinal(In);
    }
    
    /**
     * ��byte����ת��Ϊ��ʾ16����ֵ���ַ�����
     * �磺byte[]{8,18}ת��Ϊ��0813��
     * ��public static byte[] hexStr2ByteArr(String strIn)
     * ��Ϊ�����ת������
     * @param arrB ��Ҫת����byte����
     * @return ת������ַ���
     * @throws Exception �������������κ��쳣�������쳣ȫ���׳�
     */
    public static String byteArr2HexStr(byte[] arrB)
        throws Exception
    {
        int iLen = arrB.length;
        //ÿ��byte�������ַ����ܱ�ʾ�������ַ����ĳ��������鳤�ȵ�����
        StringBuffer sb = new StringBuffer(iLen * 2);
        for (int i = 0; i < iLen; i++)
        {
            int intTmp = arrB[i];
            //�Ѹ���ת��Ϊ����
            while (intTmp < 0){
                intTmp = intTmp + 256;
            }
            //С��0F������Ҫ��ǰ�油0
            if (intTmp < 16){
                sb.append("0");
            }
            sb.append(Integer.toString(intTmp, 16));
        }
        return sb.toString();
    }


    /**
     * ����ʾ16����ֵ���ַ���ת��Ϊbyte���飬
     * ��public static String byteArr2HexStr(byte[] arrB)
     * ��Ϊ�����ת������
     * @param strIn ��Ҫת�����ַ���
     * @return ת�����byte����
     * @throws Exception �������������κ��쳣�������쳣ȫ���׳�
     */
    public static final byte[] hexStr2ByteArr(String strIn)
        throws Exception
    {
        byte[] arrB = strIn.getBytes();
        int iLen = arrB.length;

		//�����ַ���ʾһ���ֽڣ������ֽ����鳤�����ַ������ȳ���2
        byte[] arrOut = new byte[iLen / 2];
        int i = 0;
        try{
        for (; i < iLen; i = i + 2)
        {
            String strTmp = new String(arrB, i, 2);
            arrOut[i / 2] = (byte) Integer.parseInt(strTmp, 16);
        }
        }catch(Exception err){
        	System.out.println("the i is" + i + " and the err is :" + err.getMessage());
        }
        
        
        return arrOut;
        
//     	int temp[] = new int[s.length()];
//     	byte   b[] = new byte[s.length()/2];
//     	for(int i = 0; i < s.length(); i ++) {			
//			temp[i] = Integer.parseInt(String.valueOf(s.charAt(i)),16);
//		}
//		int k = 0;
//		for(int j = 0;j < s.length();j += 2)
//		{
//			b[k] = (byte)(temp[j]*16 + temp[j+1]);
//			k++;
//		}
//		
//		return b;

    }


}
