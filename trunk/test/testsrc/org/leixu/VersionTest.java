/**
 * 
 */
package org.leixu;

import static org.junit.Assert.*;

import org.junit.Test;

/**
 * <pre>
 * Title:�������������
 * Description: �����ܵ����� 
 * </pre>
 * @author xulei  xu.lei55@zet.com.cn
 * @version 1.00.00
 * <pre>
 * �޸ļ�¼
 *    �޸ĺ�汾:     �޸��ˣ�  �޸�����:     �޸�����: 
 * </pre>
 */
public class VersionTest {

	/**
	 * Test method for {@link org.leixu.Version#getVersion()}.
	 */
	@Test
	public void testGetVersion() {
		assertEquals("jdk 1.5.3", new Version().getVersion());
	}

}
