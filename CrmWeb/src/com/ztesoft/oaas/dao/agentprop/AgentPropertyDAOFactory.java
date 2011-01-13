/**
 * 
 */
package com.ztesoft.oaas.dao.agentprop;

/**
 * @author –Ì»Ò∫¿
 *
 */
public class AgentPropertyDAOFactory {

	public static AgentPropertyDAO getAgentPropertyDAO(){
		return new AgentPropertyDAOImpl();
	}
}
