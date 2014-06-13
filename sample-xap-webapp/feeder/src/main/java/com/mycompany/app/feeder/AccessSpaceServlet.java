package com.mycompany.app.feeder;

import com.mycompany.app.common.Data;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.jini.core.lease.Lease;

import org.openspaces.core.GigaSpace;
import org.openspaces.core.GigaSpaceConfigurer;
import org.openspaces.core.context.GigaSpaceContext;
import org.openspaces.core.space.UrlSpaceConfigurer;

import com.j_spaces.core.IJSpace;
import com.j_spaces.core.client.UpdateModifiers;

/**
 * A servlet which updates the space with the content of the request parameters and 
 * forwards to the SpaceContents.jsp which displays the contents of the space
 * Getting the space reference is the responsibility of subclasses
 * @author Uri Cohen
 */

public class AccessSpaceServlet extends HttpServlet {
	
	private static final long serialVersionUID = 1L;
	
	protected GigaSpace gigaSpace; 
	
	private long numberOfTypes = 10;	
	/**
	 * Initialize the servlet by creating a space reference using the OpenSpaces API 
	 */
	@Override
	public void init() throws ServletException { 
		initGigaSpaces();
		if (gigaSpace == null) { 
			throw new IllegalStateException("gigaSpace cannot be null"); 
		}			
	}

	/**
	 * Creates a reference to the remote space. Make sure you start a data grid with the 
	 * name "mySpace" before this code is called  
	 */
	protected void initGigaSpaces() {		 
		IJSpace space = new UrlSpaceConfigurer("jini://*/*/space").lookupTimeout(20000).space(); 
		gigaSpace = new GigaSpaceConfigurer(space).gigaSpace();
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doPost(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        System.out.println("******* Got Request *******");
		String userId = request.getSession().getId();
		String dataName = request.getParameter("dataname");
        String dataValue = request.getParameter("datavalue");
        if (isStringNotEmpty(dataName) && isStringNotEmpty(dataValue)) {            
            gigaSpace.write(new Data(0, "Name: " + dataName + "\tValue: " + dataValue), 
            		Lease.FOREVER, 30000, UpdateModifiers.UPDATE_OR_WRITE);
        }
        Data[] spaceEntries = gigaSpace.readMultiple(new Data(), Integer.MAX_VALUE);
        request.setAttribute("spaceEntries", spaceEntries); 
        request.getRequestDispatcher("SpaceContents.jsp").forward(request, response);
	}

	private boolean isStringNotEmpty(String str) {
		return (str != null && str.trim().length() > 0);  
	}
	
	public void setNumberOfTypes(long numberOfTypes) {
        this.numberOfTypes = numberOfTypes;
    }

}
