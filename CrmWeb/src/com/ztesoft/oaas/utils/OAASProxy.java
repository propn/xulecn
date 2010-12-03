package com.ztesoft.oaas.utils;

import java.io.IOException;

import com.ztesoft.oaas.common.socketservice.AuthException;
import com.ztesoft.oaas.common.socketservice.AuthService;
import com.ztesoft.oaas.common.socketservice.StructPackage;
import com.ztesoft.oaas.struct.ChangePasswordRequest;
import com.ztesoft.oaas.struct.ChangePasswordRespond;
import com.ztesoft.oaas.struct.EncryptRequest;
import com.ztesoft.oaas.struct.EncryptRespond;
import com.ztesoft.oaas.struct.LoginRequest;
import com.ztesoft.oaas.struct.LoginRespond;
import com.ztesoft.oaas.struct.RoleState;

public class OAASProxy
{
    public static LoginRespond login(LoginRequest request) throws AuthException,IOException
    {
        StructPackage reqPack = new StructPackage("LoginRequest");
        reqPack.setRepStructName("LoginRespond");
        
        reqPack.addInt("socketId", 0);
        reqPack.addString("staffCode", request.getStaffCode());
        reqPack.addString("password", request.getPassword());
        reqPack.addString("ipAddress", request.getIpAddress());
        reqPack.addString("hostName", request.getHostName());
        reqPack.addString("mac", request.getMac());
        reqPack.addString("roleNum", request.getRoleId()==null ? "0" : String.valueOf(request.getRoleId().length));
        reqPack.addStringArray("roleId", request.getRoleId());

        StructPackage resPack = AuthService.getInstance().sendRequest(reqPack);
        LoginRespond respond = new LoginRespond();
        
        respond.setStrMsgNo(resPack.getString("strMsgNo"));
        respond.setSuccess(resPack.getString("success"));
        respond.setReason(resPack.getString("reason"));
        respond.setSocketId(resPack.getString("socketId"));
        respond.setPartyRoleId(resPack.getString("partyRoleId"));
        respond.setStaffCode(resPack.getString("staffCode"));
        respond.setPassword(resPack.getString("password"));
        respond.setPartyName(new String(resPack.getString("partyName").getBytes("iso8859-1"),"GBK"));
        respond.setBirthDate(resPack.getString("birthDate"));
        respond.setSocialIdType(resPack.getString("socialIdType"));
        respond.setSocialId(resPack.getString("socialId"));
        respond.setOrgCode(resPack.getString("orgCode"));
        respond.setOrgName(resPack.getString("orgName"));
        respond.setDutyId(resPack.getString("dutyId"));
        respond.setAddi1(resPack.getString("addi1"));
        respond.setSrvTime(resPack.getString("srvTime"));
        respond.setIsEnd(resPack.getString("isEnd"));
        if( !"1".equals(respond.getSuccess() )){
        	return respond ;
        }
        StructPackage[] arrSpRoleStates = resPack.getStructArray("roleState");
        if(arrSpRoleStates!=null)
        {
            RoleState[] arrRoleStates = new RoleState[arrSpRoleStates.length];
            for(int i=0; i<arrSpRoleStates.length; i++)
            {
                arrRoleStates[i] = new RoleState();
                arrRoleStates[i].setPrivilegeId(arrSpRoleStates[i].getString("privilegeId"));
                arrRoleStates[i].setPrivilegeCode(arrSpRoleStates[i].getString("privilegeCode"));
                arrRoleStates[i].setRegionId(arrSpRoleStates[i].getString("regionId"));
                arrRoleStates[i].setPermitionFlag(arrSpRoleStates[i].getString("permitionFlag"));
                arrRoleStates[i].setPartyRoleSchema(arrSpRoleStates[i].getString("partyRoleSchema"));
                arrRoleStates[i].setValidTime(arrSpRoleStates[i].getString("validTime"));
            }
            respond.setRoleState(arrRoleStates);
        }
        
        return respond;
    }
    
    public static ChangePasswordRespond changePassword(ChangePasswordRequest request) throws Exception
    {
        StructPackage reqPack = new StructPackage("ChangePasswordRequest");
        reqPack.setRepStructName("ChangePasswordRespond");
        
        reqPack.addString("staffCode", request.getStaffCode());
        reqPack.addString("preemption", request.getPreemption());
        reqPack.addString("oldPassword", request.getOldPassword());
        reqPack.addString("newPassword", request.getNewPassword());

        StructPackage resPack = AuthService.getInstance().sendRequest(reqPack);
        ChangePasswordRespond respond = new ChangePasswordRespond();
        
        respond.setStrMsgNo(resPack.getString("strMsgNo"));
        respond.setSuccess(resPack.getString("success"));
        respond.setReason(resPack.getString("reason"));
        
        return respond;
    }
    
    public static EncryptRespond encrypt(EncryptRequest request) throws Exception
    {
        StructPackage reqPack = new StructPackage("EncryptRequest");
        reqPack.setRepStructName("EncryptRespond");
        
        reqPack.addString("flag", request.getFlag());
        reqPack.addString("proclaimedBuff", request.getProclaimedBuff());
        reqPack.addString("secretBuff", request.getSecretBuff());
        
        StructPackage resPack = AuthService.getInstance().sendRequest(reqPack);
        EncryptRespond respond = new EncryptRespond();
        
        respond.setStrMsgNo(resPack.getString("strMsgNo"));
        respond.setSuccess(resPack.getString("success"));
        respond.setReason(resPack.getString("reason"));
        respond.setFlag(resPack.getString("flag"));
        respond.setProclaimedBuff(resPack.getString("proclaimedBuff"));
        respond.setSecretBuff(resPack.getString("secretBuff"));
        respond.setStrResultBuff(resPack.getString("strResultBuff"));
        
        return respond;
    }
    
    /*
    public static void main(String[] args) throws Exception
    {
        if(args.length<2)
        {
            System.out.println("Usage: java com.ztesoft.oaas.service.OAASProxy -option params");
            System.out.println("  options:");
            System.out.println("    -L Login, params are \"staffcode password\"");
            System.out.println("    -E Encrypt, params are \"flag strProclaim strSecret\"");
            System.out.println("       for param flag, T encrypt, F decrypt, S.");
            System.out.println("    -C Changepassword, params are \"staffcode preemptionflag oldpass newpass\"");
            System.out.println("       for param preemptionflag, T true, F false");
            return;
        }
        if(args[0].equals("-L"))
        {
            if(args.length<3)
            {
                return;
            }
            LoginRequest reqLogin = new LoginRequest();
            reqLogin.setStaffCode(args[1]);
            reqLogin.setPassword(args[2]);
            LoginRespond resLogin = login(reqLogin);
            System.out.println("strMsgNo: " +  resLogin.getStrMsgNo());
            System.out.println("success: " +  resLogin.getSuccess());
            System.out.println("reason: " +  resLogin.getReason());
            System.out.println("socketId: " +  resLogin.getSocketId());
            System.out.println("partyRoleId: " +  resLogin.getPartyRoleId());
            System.out.println("staffCode: " +  resLogin.getStaffCode());
            System.out.println("password: " +  resLogin.getPassword());
            System.out.println("partyName: " +  resLogin.getPartyName());
            System.out.println("birthDate: " +  resLogin.getBirthDate());
            System.out.println("socialIdType: " +  resLogin.getSocialIdType());
            System.out.println("socialId: " +  resLogin.getSocialId());
            System.out.println("orgCode: " +  resLogin.getOrgCode());
            System.out.println("orgName: " +  resLogin.getOrgName());
            System.out.println("dutyId: " +  resLogin.getDutyId());
            System.out.println("addi1: " +  resLogin.getAddi1());
            System.out.println("srvTime: " +  resLogin.getSrvTime());
            System.out.println("isEnd: " +  resLogin.getIsEnd());
            RoleState[] roles = resLogin.getRoleState();
            System.out.println("roleNum: " +  roles.length);
            for(int i=0; i<roles.length; i++)
            {
                System.out.println("role[" + i + "]:");
                System.out.println("  privilegeId: " + roles[i].getPrivilegeId());
                System.out.println("  privilegeCode: " + roles[i].getPrivilegeCode());
                System.out.println("  regionId: " + roles[i].getRegionId());
                System.out.println("  permitionFlag: " + roles[i].getPermitionFlag());
                System.out.println("  partyRoleSchema: " + roles[i].getPartyRoleSchema());
                System.out.println("  validTime: " + roles[i].getValidTime());
            }
        }
        else if(args[0].equals("-E"))
        {
            if(args.length<4)
            {
                System.out.println("deficient params");
                return;
            }
            EncryptRequest reqEncrypt = new EncryptRequest();
            reqEncrypt.setFlag(args[1]);
            reqEncrypt.setProclaimedBuff(args[2]);
            reqEncrypt.setSecretBuff(args[3]);
            EncryptRespond resEncrypt = encrypt(reqEncrypt);
            System.out.println("strMsgNo: " + resEncrypt.getStrMsgNo());
            System.out.println("success: " + resEncrypt.getSuccess());
            System.out.println("reason: " + resEncrypt.getReason());
            System.out.println("flag: " + resEncrypt.getFlag());
            System.out.println("proclaimedBuff: " + resEncrypt.getProclaimedBuff());
            System.out.println("secretBuff: " + resEncrypt.getSecretBuff());
            System.out.println("strResultBuff: " + resEncrypt.getStrResultBuff());
        }
        else if(args[0].equals("-C"))
        {
            if(args.length<5)
            {
                System.out.println("deficient params");
                return;
            }
            ChangePasswordRequest reqCP = new ChangePasswordRequest();
            reqCP.setStaffCode(args[1]);
            reqCP.setPreemption(args[2]);
            reqCP.setOldPassword(args[3]);
            reqCP.setNewPassword(args[4]);
            ChangePasswordRespond resCP = changePassword(reqCP);
            System.out.println("strMsgNo: " + resCP.getStrMsgNo());
            System.out.println("success: " + resCP.getSuccess());
            System.out.println("reason: " + resCP.getReason());
        }
    }
    */
}
