/**
 * 
 */
package org.leixu;

import static org.junit.Assert.*;

import org.junit.Test;

/**
 * <pre>
 * Title:程序的中文名称
 * Description: 程序功能的描述 
 * </pre>
 * @author xulei  xu.lei55@zet.com.cn
 * @version 1.00.00
 * <pre>
 * 修改记录
 *    修改后版本:     修改人：  修改日期:     修改内容: 
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
