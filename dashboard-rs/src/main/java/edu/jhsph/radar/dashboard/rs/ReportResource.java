/*
 * Copyright (C) 2016 John Hopkings Bloomberg School of Public Health
 *
 * Licensed under the MIT License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at
 *
 * https://opensource.org/licenses/MIT
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 */
package  edu.jhsph.radar.dashboard.rs;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;
import javax.inject.Named;
import javax.inject.Singleton;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.springframework.security.core.context.SecurityContextHolder;

import edu.jhsph.radar.dashboard.rs.dao.ReportDao;
import edu.jhsph.radar.dashboard.rs.model.LinkData;
import edu.jhsph.radar.dashboard.rs.model.LinksData;


@Path("report")
@Singleton
@Named
public class ReportResource {
	@Inject
	protected ReportDao springJdbcReportDao;

    @GET
    @Path("links")
    @Produces({MediaType.APPLICATION_JSON})
    public Response getLinks() {
    	
    	String username = "";
 		if (SecurityContextHolder.getContext() != null && SecurityContextHolder.getContext().getAuthentication() != null) {
 			username = SecurityContextHolder.getContext().getAuthentication().getName();
 		}
 		
 		List<LinkData> links = new ArrayList<LinkData>();
 		//This could depend on user access
 		links.add(new LinkData("household", "rs/report/household"));
 		links.add(new LinkData("woman", "rs/report/woman"));
 		links.add(new LinkData("caretaker", "rs/report/caretaker"));
 		links.add(new LinkData("eligWoman", "rs/report/eligWoman"));
 		links.add(new LinkData("eligChild", "rs/report/eligChild"));
 		links.add(new LinkData("totals", "rs/report/totals"));
 		links.add(new LinkData("ratioChild_5_4", "rs/report/ratioChild_5_4"));
 		links.add(new LinkData("ratioWoman_14_15", "rs/report/ratioWoman_14_15"));
 		links.add(new LinkData("ratioWoman_50_49", "rs/report/ratioWoman_50_49"));
 		links.add(new LinkData("duplicateHouseholds", "rs/report/duplicateHouseholds"));
 		links.add(new LinkData("callbacksTotals", "rs/report/callbacksTotals"));
		links.add(new LinkData("householdCallbacks", "rs/report/householdCallbacks"));
		links.add(new LinkData("womanCallbacks", "rs/report/womanCallbacks"));
		links.add(new LinkData("childCallbacks", "rs/report/childCallbacks"));
		 		 		
    	LinksData linksData = new LinksData(username, links);
    	
        return Response.ok(linksData).build();
    }

    @GET
    @Path("household")
    @Produces({MediaType.APPLICATION_JSON})
    public Response getHousehold(@QueryParam("idType") String idType) {
        return Response.ok(springJdbcReportDao.getHousehold(idType)).build();
    }

    @GET
    @Path("woman")
    @Produces({MediaType.APPLICATION_JSON})
    public Response getWoman(@QueryParam("idType") String idType) {
        return Response.ok(springJdbcReportDao.getWoman(idType)).build();
    }

    @GET
    @Path("caretaker")
    @Produces({MediaType.APPLICATION_JSON})
    public Response getCaretaker(@QueryParam("idType") String idType) {
        return Response.ok(springJdbcReportDao.getCaretaker(idType)).build();
    }

    @GET
    @Path("eligWoman")
    @Produces({MediaType.APPLICATION_JSON})
    public Response getEligWoman(@QueryParam("idType") String idType) {
        return Response.ok(springJdbcReportDao.getEligibleWoman(idType)).build();
    }

    @GET
    @Path("eligChild")
    @Produces({MediaType.APPLICATION_JSON})
    public Response getEligChild(@QueryParam("idType") String idType) {
        return Response.ok(springJdbcReportDao.getEligibleChild(idType)).build();
    }

    @GET
    @Path("totals")
    @Produces({MediaType.APPLICATION_JSON})
    public Response getTotals() {
        return Response.ok(springJdbcReportDao.getTotals()).build();
    }

    @GET
    @Path("ratioChild_5_4")
    @Produces({MediaType.APPLICATION_JSON})
    public Response getRatioChild54(@QueryParam("idType") String idType) {
        return Response.ok(springJdbcReportDao.getChild_5_4(idType)).build();
    }

    @GET
    @Path("ratioWoman_14_15")
    @Produces({MediaType.APPLICATION_JSON})
    public Response getRatioWoman1415(@QueryParam("idType") String idType) {
        return Response.ok(springJdbcReportDao.getWoman_14_15(idType)).build();
    }

    @GET
    @Path("ratioWoman_50_49")
    @Produces({MediaType.APPLICATION_JSON})
    public Response getRatioWoman5049(@QueryParam("idType") String idType) {
        return Response.ok(springJdbcReportDao.getWoman_50_49(idType)).build();
    }

    @GET
    @Path("duplicateHouseholds")
    @Produces({MediaType.APPLICATION_JSON})
    public Response getDuplicateHouseholds() {
        return Response.ok(springJdbcReportDao.getDuplicateHouseholds()).build();
    }

    @GET
    @Path("callbacksTotals")
    @Produces({MediaType.APPLICATION_JSON})
    public Response getCallbacksTotals() {
        return Response.ok(springJdbcReportDao.getCallbacksTotals()).build();
    }

    @GET
    @Path("householdCallbacks")
    @Produces({MediaType.APPLICATION_JSON})
    public Response getHouseholdCallbacks() {
        return Response.ok(springJdbcReportDao.getHouseholdCallbacks()).build();
    }

    @GET
    @Path("womanCallbacks")
    @Produces({MediaType.APPLICATION_JSON})
    public Response getWomanCallbacks() {
        return Response.ok(springJdbcReportDao.getWomanCallbacks()).build();
    }

    @GET
    @Path("childCallbacks")
    @Produces({MediaType.APPLICATION_JSON})
    public Response getChildCallbacks() {
        return Response.ok(springJdbcReportDao.getChildCallbacks()).build();
    }
}
