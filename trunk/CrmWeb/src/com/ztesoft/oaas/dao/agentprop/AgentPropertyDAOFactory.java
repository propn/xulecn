/**
 * 
 */
package com.ztesoft.oaas.dao.agentprop;

/**
 * @author �����
 *
 */
public class AgentPropertyDAOFactory {

	public static AgentPropertyDAO getAgentPropertyDAO(){
		return new AgentPropertyDAOImpl();
	}
}
