package org.leixu.jersey.resources;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

@Path("/hello")
public class HelloResource {
	@GET
	@Produces({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON,
			MediaType.TEXT_PLAIN })
	public String sayHello() {
		return "<a>Hello Jersey</a>";
	}

	@GET
	@Path("map")
	@Produces({ MediaType.APPLICATION_JSON, MediaType.TEXT_PLAIN,
			MediaType.APPLICATION_XML })
	public Map getMap() {
		Map map = new HashMap();
		map.put("userName", "xulei");
		map.put("age", 27);
		map.put("sex", "man");
		return map;
	}

	@GET
	@Path("list")
	@Produces({ MediaType.APPLICATION_JSON, MediaType.TEXT_PLAIN,
			MediaType.APPLICATION_XML })
	public List getList() {
		Map map = new HashMap();
		map.put("userName", "xulei");
		map.put("age", 27);
		map.put("sex", "man");
		List rst = new ArrayList();
		rst.add(map);
		return rst;
	}

	@PUT
	@Path("put")
	@Produces({ MediaType.APPLICATION_JSON })
	@Consumes(MediaType.APPLICATION_JSON)
	public String put() {
		Map map = new HashMap();
		map.put("userName", "xulei");
		map.put("age", 27);
		map.put("sex", "man");
		List rst = new ArrayList();
		rst.add(map);
		return "";
	}

}
