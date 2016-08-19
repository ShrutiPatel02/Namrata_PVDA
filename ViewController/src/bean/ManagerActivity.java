package bean;

import java.util.Map;

import javax.faces.context.FacesContext;
import javax.faces.event.ValueChangeEvent;

import javax.servlet.http.HttpSession;

import oracle.adf.model.BindingContext;
import oracle.adf.model.binding.DCBindingContainer;
import oracle.adf.model.binding.DCIteratorBinding;
import oracle.adf.share.ADFContext;
import oracle.adf.view.rich.component.rich.RichPopup;
import oracle.adf.view.rich.component.rich.data.RichTable;
import oracle.adf.view.rich.component.rich.input.RichSelectOneChoice;
import oracle.adf.view.rich.component.rich.nav.RichCommandButton;
import oracle.adf.view.rich.context.AdfFacesContext;
import oracle.adf.view.rich.event.DialogEvent;
import oracle.adf.view.rich.event.PopupCanceledEvent;

import oracle.binding.BindingContainer;
import oracle.binding.OperationBinding;

import oracle.jbo.Row;
import oracle.jbo.ViewObject;

import org.apache.myfaces.trinidad.event.DisclosureEvent;

//import pvda.hcmservice.WorkerInformationByPersonNumber;

import utils.JSFUtils;

public class ManagerActivity {
    
    private RichPopup createNewIncidentPopup;
    private RichPopup editIncidentPopup;
    private boolean newIncident = false;
    private String handleApproveButtonVisiblity = "";
    private RichCommandButton approveVisibleButton;

    public void setApproveVisibleButton(RichCommandButton approveVisibleButton)
    {
      this.approveVisibleButton = approveVisibleButton;
    }

    public RichCommandButton getApproveVisibleButton()
    {
      return approveVisibleButton;
    }

    public void setHandleApproveButtonVisiblity(String handleApproveButtonVisiblity)
    {
      this.handleApproveButtonVisiblity = handleApproveButtonVisiblity;
    }

    public String getHandleApproveButtonVisiblity()
    {
      BindingContainer bindings = getBindings();
      BindingContext bindingctx = BindingContext.getCurrent();
      DCBindingContainer bindingsImpl = (DCBindingContainer) bindings;
      try
      {
        DCIteratorBinding iter1 = bindingsImpl.findIteratorBinding("EmployeeHRCheckVO1Iterator");
        Map sessionScope = ADFContext.getCurrent().getSessionScope();
        iter1.getViewObject().setNamedWhereClauseParam("p_empId", sessionScope.get("loginEmpId"));
        iter1.getViewObject().executeQuery();
        Row r[] = iter1.getAllRowsInRange();
        if (r.length > 0)
        {
          session.setAttribute("approveButtonVis", true);
        }
        else
        {
          session.setAttribute("approveButtonVis", false);
        }
      }
      catch (Exception e)
      {
        e.printStackTrace();
      }
      AdfFacesContext.getCurrentInstance().addPartialTarget(JSFUtils.findComponentInRoot("g6"));
      AdfFacesContext.getCurrentInstance().addPartialTarget(JSFUtils.findComponentInRoot("ctb18"));
      AdfFacesContext.getCurrentInstance().addPartialTarget(JSFUtils.findComponentInRoot("pc1"));
      AdfFacesContext.getCurrentInstance().addPartialTarget(JSFUtils.findComponentInRoot("sdh1"));
      AdfFacesContext.getCurrentInstance().addPartialTarget(JSFUtils.findComponentInRoot("gc1"));
      AdfFacesContext.getCurrentInstance().addPartialTarget(managerIncidentReportTable);
      return handleApproveButtonVisiblity;
    }
    private HttpSession session = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(true);

    public void setNewIncident(boolean newIncident)
    {
      this.newIncident = newIncident;
    }

    public boolean isNewIncident()
    {
      return newIncident;
    }

    public void setSaveMessage(String saveMessage)
    {
      this.saveMessage = saveMessage;
    }

    public String getSaveMessage()
    {
      return saveMessage;
    }
    private String saveMessage = "";

    public void setEditIncidentPopup(RichPopup editIncidentPopup)
    {
      this.editIncidentPopup = editIncidentPopup;
    }

    public RichPopup getEditIncidentPopup()
    {
      return editIncidentPopup;
    }
    private RichTable managerIncidentReportTable;

    public void setManagerIncidentReportTable(RichTable managerIncidentReportTable)
    {
      this.managerIncidentReportTable = managerIncidentReportTable;
    }

    public RichTable getManagerIncidentReportTable()
    {
      return managerIncidentReportTable;
    }

    public void setCreateNewIncidentPopup(RichPopup createNewIncidentPopup)
    {
      this.createNewIncidentPopup = createNewIncidentPopup;
    }

    public RichPopup getCreateNewIncidentPopup()
    {
      return createNewIncidentPopup;
    }

    public ManagerActivity()
    {
      super();
    }

    /**
     * Method to display create new incident popup.
     * @return String
     */
    public String createNewIncident()
    {
      BindingContainer bindings = getBindings();
      BindingContext bindingctx = BindingContext.getCurrent();
      DCBindingContainer bindingsImpl = (DCBindingContainer) bindings;
      try
      {
        DCIteratorBinding iter1 = bindingsImpl.findIteratorBinding("ManagerIncidentReportVO1Iterator");
        session.setAttribute("newIncident", true);
        setNewIncident(true);
        ViewObject incidentReportVo = iter1.getViewObject();
        Map sessionScope = ADFContext.getCurrent().getSessionScope();
        //Number loginEmpId = (Number) sessionScope.get("loginEmpId");
        incidentReportVo.setNamedWhereClauseParam("P_ManagerId", sessionScope.get("loginEmpId"));
        incidentReportVo.executeQuery();
        OperationBinding operationBinding = bindings.getOperationBinding("CreateInsert");
        Object result = operationBinding.execute();
        if (!operationBinding.getErrors().isEmpty())
        {
          return null;
        }
        incidentReportVo.getViewObject().setRangeSize(-1);
        Row r = incidentReportVo.getViewObject().getRowAtRangeIndex(0);
        r.setNewRowState(Row.STATUS_NEW);
        r.setAttribute("Reportingmanager", sessionScope.get("loginEmpId"));
      }
      catch (Exception e)
      {
        e.printStackTrace();
      }
      //r.setAttribute("Hrid", new oracle.jbo.domain.Number(4));
      //r.setNewRowState(Row.STATUS_NEW);
      //        RichPopup.PopupHints hints = new RichPopup.PopupHints();
      //                    this.getCreateNewIncidentPopup().show(hints);

      //AdfFacesContext.getCurrentInstance().addPartialTarget(managerIncidentReportTable);
      return null;
    }

    public String invokeEditIncidentPopup()
    {
      RichPopup.PopupHints hints = new RichPopup.PopupHints();
      getEditIncidentPopup().show(hints);
      return null;
    }

    public void incidentPopupCancelListener(PopupCanceledEvent popupCanceledEvent)
    {
      BindingContext bindingctx = BindingContext.getCurrent();
      BindingContainer bindings = bindingctx.getCurrentBindingsEntry();
      DCBindingContainer bindingsImpl = (DCBindingContainer) bindings;
      System.out.println("Am in Else");
      DCIteratorBinding iter1 = null;
      iter1 = bindingsImpl.findIteratorBinding("ManagerIncidentReportVO1Iterator");

      ViewObject incidentReportVo = iter1.getViewObject();
      Map sessionScope = ADFContext.getCurrent().getSessionScope();
      //Number loginEmpId = (Number) sessionScope.get("loginEmpId");
      //try {
      incidentReportVo.setNamedWhereClauseParam("P_ManagerId", sessionScope.get("loginEmpId"));
      // } catch (SQLException e) {
      //e.printStackTrace();
      // }
      incidentReportVo.executeQuery();
      //AdfFacesContext.getCurrentInstance().addPartialTarget(managerIncidentReportTable);
    }

    public BindingContainer getBindings()
    {
      return BindingContext.getCurrent().getCurrentBindingsEntry();
    }

    public void createNewIncidentListener(DialogEvent dialogEvent)
    {
      try
      {
        BindingContext bindingctx = BindingContext.getCurrent();
        BindingContainer bindings = bindingctx.getCurrentBindingsEntry();
        DCBindingContainer bindingsImpl = (DCBindingContainer) bindings;
        if (dialogEvent.getOutcome() == DialogEvent.Outcome.ok)
        {
          System.out.println("Am in OK");
          DCIteratorBinding iter = bindingsImpl.findIteratorBinding("ManagerIncidentReportVO1Iterator");
          System.out.println("iteriteriter" + iter);
          Row rw = iter.getCurrentRow();
          bindings = getBindings();
          System.out.println("befor  Commit");
          OperationBinding operationBinding = bindings.getOperationBinding("Commit");

          Object result = operationBinding.execute();

          if (!operationBinding.getErrors().isEmpty())
          {
            // Display Error Message
            System.out.println("Display error Message");
          }

        }
        else if (dialogEvent.getOutcome() == DialogEvent.Outcome.cancel)
        {
          DCIteratorBinding iter1 = null;
          iter1 = bindingsImpl.findIteratorBinding("ManagerIncidentReportVO1Iterator");

          ViewObject incidentReportVo = iter1.getViewObject();
          Map sessionScope = ADFContext.getCurrent().getSessionScope();
          //Number loginEmpId = (Number) sessionScope.get("loginEmpId");
          incidentReportVo.setNamedWhereClauseParam("P_ManagerId", sessionScope.get("loginEmpId"));
          incidentReportVo.executeQuery();
        }
        AdfFacesContext.getCurrentInstance().addPartialTarget(managerIncidentReportTable);
      }
      catch (Exception e)
      {
        // TODO: Add catch code
        e.printStackTrace();
      }
    }

    public String approveIncident()
    {
      BindingContext bindingctx = BindingContext.getCurrent();
      BindingContainer bindings = bindingctx.getCurrentBindingsEntry();
      DCBindingContainer bindingsImpl = (DCBindingContainer) bindings;
      DCIteratorBinding iter = bindingsImpl.findIteratorBinding("ManagerIncidentReportVO1Iterator");
      Row currentRow = iter.getViewObject().getCurrentRow();
      currentRow.setAttribute("Status", "APPROVED");
      OperationBinding operationBinding = bindings.getOperationBinding("Commit");
      Object result = operationBinding.execute();
      StringBuilder sb = new StringBuilder();
      try
      {
        //WorkerInformationByPersonNumber ab = new WorkerInformationByPersonNumber();
//        WorkerInformationByPersonNumber workerInformationByPersonNumber =
//          new WorkerInformationByPersonNumber("31008", "2015-10-09");
//        workerInformationByPersonNumber.invoke();
//        sb.append("Disciplinary action details updated to HCM. Assignment Id: " +
//                  workerInformationByPersonNumber.getAssignmentId());
//        System.out.println(workerInformationByPersonNumber.getAssignmentId());
        //sb = callWebServiceToPerformAction();
      }
      catch (Exception e)
      {
        sb.append("Disciplinary action details updated to HCM. Assignment Id: ");
        e.printStackTrace();
      }
      saveMessage = sb.toString();
      RichPopup.PopupHints hints = new RichPopup.PopupHints();
      RichPopup rp = (RichPopup) JSFUtils.findComponentInRoot("p2");
      rp.show(hints);
      return null;

    }

    public String saveIncident()
    {
      try
      {
        BindingContext bindingctx = BindingContext.getCurrent();
        BindingContainer bindings = bindingctx.getCurrentBindingsEntry();
        DCBindingContainer bindingsImpl = (DCBindingContainer) bindings;
        DCIteratorBinding iter = bindingsImpl.findIteratorBinding("ManagerIncidentReportVO1Iterator");
        System.out.println("iteriteriter" + iter);
        Row rw = iter.getCurrentRow();
        bindings = getBindings();
        String disActionValue = "";
        if (JSFUtils.resolveExpression("#{bindings.Disciplinaryactioncode1.selectedValue}")!=null && !JSFUtils.resolveExpression("#{bindings.Disciplinaryactioncode1.selectedValue}").equals(""))
        {
          disActionValue =
            JSFUtils.resolveExpressionAsString("#{bindings.Disciplinaryactioncode1.selectedValue.attributeValues[1]}");
        }

        if ((disActionValue.toLowerCase().contains("suspension") || disActionValue.toLowerCase().contains("termination")))
        {
          rw.setAttribute("Status", "SUBMITTED TO HR");
        }
        else
        {
          rw.setAttribute("Status", "SUBMITTED");
        }
        //            if (rw.getAttribute("Hrid") == null &&
        //                rw.getAttribute("Status").toString().equalsIgnoreCase("SUBMITTED TO HR")) {
        //                saveMessage =
        //                    "Incident cannot be submitted to HR since Incident Action Type is not discretionary.Please select different Status.";
        //                RichPopup.PopupHints hints = new RichPopup.PopupHints();
        //                RichPopup rp = (RichPopup) JSFUtils.findComponentInRoot("p2");
        //                rp.show(hints);
        //            } else {
        System.out.println("befor  Commit");
        OperationBinding operationBinding = bindings.getOperationBinding("Commit");

        Object result = operationBinding.execute();
        if (!operationBinding.getErrors().isEmpty())
        {
          // Display Error Message
          System.out.println("Display error Message");
        }
        StringBuilder sb = new StringBuilder();
        //                if (rw.getAttribute("Status").toString().equalsIgnoreCase("APPROVED")) {
        //                    try{
        //                    sb = callWebServiceToPerformAction();
        //                    }catch(Exception e){
        //                            sb.append("Disciplinary action details updated to HCM.");
        //                        e.printStackTrace();
        //                        }
        //                } else {
        String empName = JSFUtils.resolveExpressionAsString("#{bindings.Employeeid1.selectedValue.attributeValues[1]}");

        boolean newIncidentChk =
          session.getAttribute("newIncident") != null? (Boolean) session.getAttribute("newIncident"): false;
        if (newIncidentChk)
        {
          sb.append("A New Incident has been created against ").append(empName + ".");
        }
        else
        {
          sb.append("Incident").append("(").append(rw.getAttribute("Incidentid")).append(") ").append("against ").append(empName).append(" updated.");
        }
        //}
        //workerService_Service.
        newIncident = false;
        session.setAttribute("newIncident", false);
        saveMessage = sb.toString();
        RichPopup.PopupHints hints = new RichPopup.PopupHints();
        RichPopup rp = (RichPopup) JSFUtils.findComponentInRoot("p2");
        rp.show(hints);
        // }
      }
      catch (Exception e)
      {
        // TODO: Add catch code
        e.printStackTrace();
      }
      return null;
    }

    public String cancelIncidentDetails()
    {
      setNewIncident(false);
      session.setAttribute("newIncident", false);
      //        session.setAttribute("approveButtonVis", false);
      //                AdfFacesContext.getCurrentInstance().addPartialTarget(JSFUtils.findComponentInRoot("g6"));
      //                AdfFacesContext.getCurrentInstance().addPartialTarget(JSFUtils.findComponentInRoot("ctb18"));
      //                AdfFacesContext.getCurrentInstance().addPartialTarget(JSFUtils.findComponentInRoot("pc1"));
      //                AdfFacesContext.getCurrentInstance().addPartialTarget(JSFUtils.findComponentInRoot("sdh1"));
      //                AdfFacesContext.getCurrentInstance().addPartialTarget(JSFUtils.findComponentInRoot("gc1"));
      //                AdfFacesContext.getCurrentInstance().addPartialTarget(managerIncidentReportTable);
      BindingContext bindingctx = BindingContext.getCurrent();
      BindingContainer bindings = bindingctx.getCurrentBindingsEntry();
      DCBindingContainer bindingsImpl = (DCBindingContainer) bindings;
      OperationBinding operationBinding = bindings.getOperationBinding("Rollback");
      operationBinding.execute();
      DCIteratorBinding iter1 = null;
      iter1 = bindingsImpl.findIteratorBinding("ManagerIncidentReportVO1Iterator");

      ViewObject incidentReportVo = iter1.getViewObject();
      //            if(newIncident){
      //            incidentReportVo.removeCurrentRow();
      //            }
      //            setNewIncident(false);
      session.setAttribute("newIncident", false);
      Map sessionScope = ADFContext.getCurrent().getSessionScope();
      //Number loginEmpId = (Number) sessionScope.get("loginEmpId");
      incidentReportVo.reset();
      incidentReportVo.setNamedWhereClauseParam("P_ManagerId", sessionScope.get("loginEmpId"));
      incidentReportVo.setRangeSize(-1);
      incidentReportVo.executeQuery();

      AdfFacesContext.getCurrentInstance().addPartialTarget(managerIncidentReportTable);
      return null;
    }

    public String updateIncident()
    {
      BindingContainer bindings = getBindings();
      DCBindingContainer bindingsImpl = (DCBindingContainer) bindings;
      OperationBinding operationBinding = bindings.getOperationBinding("Commit");
      Object result = operationBinding.execute();
      DCIteratorBinding iter1 = null;
      iter1 = bindingsImpl.findIteratorBinding("ManagerIncidentReportVO1Iterator");
      ViewObject incidentReportVo = iter1.getViewObject();
      incidentReportVo.getApplicationModule().getTransaction().commit();
      Map sessionScope = ADFContext.getCurrent().getSessionScope();
      //Number loginEmpId = (Number) sessionScope.get("loginEmpId");
      incidentReportVo.setNamedWhereClauseParam("P_ManagerId", sessionScope.get("loginEmpId"));
      incidentReportVo.executeQuery();
      getEditIncidentPopup().hide();
      AdfFacesContext.getCurrentInstance().addPartialTarget(managerIncidentReportTable);
      return null;
    }

    public String cancelEditIncidentPopup()
    {
      BindingContext bindingctx = BindingContext.getCurrent();
      BindingContainer bindings = bindingctx.getCurrentBindingsEntry();
      DCBindingContainer bindingsImpl = (DCBindingContainer) bindings;
      DCIteratorBinding iter1 = null;
      iter1 = bindingsImpl.findIteratorBinding("ManagerIncidentReportVO1Iterator");

      ViewObject incidentReportVo = iter1.getViewObject();
      Map sessionScope = ADFContext.getCurrent().getSessionScope();
      //Number loginEmpId = (Number) sessionScope.get("loginEmpId");
      incidentReportVo.setNamedWhereClauseParam("P_ManagerId", sessionScope.get("loginEmpId"));
      incidentReportVo.executeQuery();
      //AdfFacesContext.getCurrentInstance().addPartialTarget(managerIncidentReportTable);
      return null;
    }

    public String deleteIncident()
    {
      BindingContainer bindings = getBindings();
      OperationBinding operationBinding = bindings.getOperationBinding("Delete");
      DCBindingContainer bindingsImpl = (DCBindingContainer) bindings;
      Object result = operationBinding.execute();
      if (!operationBinding.getErrors().isEmpty())
      {
        return null;
      }

      System.out.println("Am in Delete Commit");
      operationBinding = bindings.getOperationBinding("Commit");
      Object result1 = operationBinding.execute();
      if (!operationBinding.getErrors().isEmpty())
      {
        // Display Error Message
        System.out.println("Display error Message");
      }
      Map sessionScope = ADFContext.getCurrent().getSessionScope();
      DCIteratorBinding iter2 = null;
      iter2 = bindingsImpl.findIteratorBinding("ManagerReporteesVO1Iterator");
      iter2.getViewObject().setNamedWhereClauseParam("P_ManagerId", sessionScope.get("loginEmpId"));
      iter2.getViewObject().executeQuery();
      DCIteratorBinding iter3 = null;
      iter3 = bindingsImpl.findIteratorBinding("IncidentStatusCountVO1Iterator");
      iter3.getViewObject().setNamedWhereClauseParam("p_managerId", sessionScope.get("loginEmpId"));
      iter3.getViewObject().executeQuery();
      AdfFacesContext.getCurrentInstance().addPartialTarget(managerIncidentReportTable);
      AdfFacesContext.getCurrentInstance().addPartialTarget(JSFUtils.findComponentInRoot("pb2"));
      AdfFacesContext.getCurrentInstance().addPartialTarget(JSFUtils.findComponentInRoot("pb1"));
      return null;
    }

    public void employeeInfoTabSelected(DisclosureEvent disclosureEvent)
    {

      if (disclosureEvent.isExpanded())
      {
        try
        {
          BindingContext bindingctx = BindingContext.getCurrent();
          BindingContainer bindings = bindingctx.getCurrentBindingsEntry();
          DCBindingContainer bindingsImpl = (DCBindingContainer) bindings;
          DCIteratorBinding iter = bindingsImpl.findIteratorBinding("ManagerIncidentReportVO1Iterator");
          System.out.println("iteriteriter" + iter);
          Row rw = iter.getCurrentRow();
          Object attribute = rw.getAttribute("Employeeid");
          DCIteratorBinding empInfoIter = bindingsImpl.findIteratorBinding("EmployeeInfoVO1Iterator");
          empInfoIter.getViewObject().setNamedWhereClauseParam("p_empId", rw.getAttribute("Employeeid"));
          empInfoIter.getViewObject().executeQuery();
          System.out.println("Check iter value:" + empInfoIter.getViewObject().getAllRowsInRange());
        }
        catch (Exception e)
        {
          e.printStackTrace();
        }

      }

    }

    public void discActionTypeValueChange(ValueChangeEvent valueChangeEvent)
    {
      if (valueChangeEvent.getNewValue() != null && valueChangeEvent.getNewValue() != valueChangeEvent.getOldValue())
      {
        BindingContext bindingctx = BindingContext.getCurrent();
        BindingContainer bindings = bindingctx.getCurrentBindingsEntry();
        DCBindingContainer bindingsImpl = (DCBindingContainer) bindings;
        DCIteratorBinding discActionTypeIter = bindingsImpl.findIteratorBinding("DisciplinaryActionTypeVO1Iterator");
        discActionTypeIter.getViewObject().setNamedWhereClauseParam("p_disTypeCode", valueChangeEvent.getNewValue());
        discActionTypeIter.getViewObject().setRangeSize(-1);
        discActionTypeIter.getViewObject().executeQuery();
        Row[] r1 = discActionTypeIter.getAllRowsInRange();
        RichSelectOneChoice rc = (RichSelectOneChoice) JSFUtils.findComponentInRoot("soc17");
        RichSelectOneChoice rc1 = (RichSelectOneChoice) JSFUtils.findComponentInRoot("soc21");
        if (r1.length > 0 && r1[0].getAttribute("Isdiscretionary").toString().equalsIgnoreCase("Y"))
        {
          DCIteratorBinding empInfoIter = bindingsImpl.findIteratorBinding("GetEmployeeHRVO1Iterator");
          Object empId = JSFUtils.resolveExpression("#{bindings.Employeeid1.inputValue}");
          empInfoIter.getViewObject().setNamedWhereClauseParam("p_empId", empId);
          empInfoIter.getViewObject().setRangeSize(-1);
          empInfoIter.getViewObject().getQuery();
          empInfoIter.getViewObject().executeQuery();
          Row[] r = empInfoIter.getAllRowsInRange();
          if (r.length > 0)
          {
            r[0].getAttributeValues();
            System.out.println("RC Value:" + r[0].getAttribute("Employeeid"));

            rc.setValue(r[0].getAttribute("Employeeid"));
            //rc1.setValue("SUBMITTED TO HR");
            //JSFUtils.setExpressionValue("#{bindings.Employeeid1.inputValue}", r[0].getAttribute("Employeeid"));
            AdfFacesContext.getCurrentInstance().addPartialTarget(JSFUtils.findComponentInRoot("soc17"));
            //AdfFacesContext.getCurrentInstance().addPartialTarget(JSFUtils.findComponentInRoot("soc21"));
          }
        }
        else
        {
          rc.resetValue();
          //rc1.setValue("SUBMITTED");
          AdfFacesContext.getCurrentInstance().addPartialTarget(JSFUtils.findComponentInRoot("soc17"));
          //AdfFacesContext.getCurrentInstance().addPartialTarget(JSFUtils.findComponentInRoot("soc21"));
        }
      }

    }

    public void deleteIncidentListener(DialogEvent dialogEvent)
    {
      try
      {
        if (dialogEvent.getOutcome() == DialogEvent.Outcome.ok)
        {
          deleteIncident();
        }
      }
      catch (Exception e)
      {
        e.printStackTrace();
      }
    }

    public void saveIncidentListener(DialogEvent dialogEvent)
    {
      BindingContext bindingctx = BindingContext.getCurrent();
      BindingContainer bindings = bindingctx.getCurrentBindingsEntry();
      DCBindingContainer bindingsImpl = (DCBindingContainer) bindings;
      try
      {
        if (dialogEvent.getOutcome() == DialogEvent.Outcome.ok)
        {
          DCIteratorBinding iter1 = null;
          iter1 = bindingsImpl.findIteratorBinding("ManagerIncidentReportVO1Iterator");

          ViewObject incidentReportVo = iter1.getViewObject();
          Map sessionScope = ADFContext.getCurrent().getSessionScope();
          incidentReportVo.setNamedWhereClauseParam("P_ManagerId", sessionScope.get("loginEmpId"));
          incidentReportVo.executeQuery();
          DCIteratorBinding iter2 = null;
          iter2 = bindingsImpl.findIteratorBinding("ManagerReporteesVO1Iterator");
          iter2.getViewObject().setNamedWhereClauseParam("P_ManagerId", sessionScope.get("loginEmpId"));
          iter2.getViewObject().executeQuery();
          DCIteratorBinding iter3 = null;
          iter3 = bindingsImpl.findIteratorBinding("IncidentStatusCountVO1Iterator");
          iter3.getViewObject().setNamedWhereClauseParam("p_managerId", sessionScope.get("loginEmpId"));
          iter3.getViewObject().executeQuery();
          AdfFacesContext.getCurrentInstance().addPartialTarget(managerIncidentReportTable);

        }
        RichPopup rp = (RichPopup) JSFUtils.findComponentInRoot("p2");
        rp.hide();
        AdfFacesContext.getCurrentInstance().addPartialTarget(JSFUtils.findComponentInRoot("pb2"));
        AdfFacesContext.getCurrentInstance().addPartialTarget(JSFUtils.findComponentInRoot("pb1"));
      }
      catch (Exception e)
      {
        e.printStackTrace();
      }
    }

    //  private StringBuilder callWebServiceToPerformAction()
    //  {
    //    StringBuilder sb = new StringBuilder();
    //    sb.append("Disciplinary action details updated to HCM.");
    //    //String respMsg = "Disciplinary action updated to HCM.";
    //    WorkerServiceSoapHttpPortClient wsp = new WorkerServiceSoapHttpPortClient();
    //    WorkerService_Service workerService_Service = new WorkerService_Service();
    //    // Configure security feature
    //    SecurityPoliciesFeature securityFeatures =
    //      new SecurityPoliciesFeature(new String[] { "oracle/http_basic_auth_over_ssl_client_policy" });
    //    WorkRelationshipUserKey workRelationshipUserKey = new WorkRelationshipUserKey();
    //    //workRelationshipUserKey.setLegalEmployerName(new JAXBElement("CTS US LE"));
    //
    //    Termination terminationDetails = new Termination();
    //    ActionsList actionList = new ActionsList();
    //    WorkerService workerService = workerService_Service.getWorkerServiceSoapHttpPort(securityFeatures);
    //
    //
    //    WSBindingProvider wsbp = (WSBindingProvider) workerService;
    //    wsbp.getRequestContext().put(BindingProvider.USERNAME_PROPERTY, "USER.SENTHIL");
    //    wsbp.getRequestContext().put(BindingProvider.PASSWORD_PROPERTY, "Welcome12#");
    //    // Get the request context to set the outgoing addressing properties
    //    try
    //    {
    //      workerService.terminateWorkRelationship(workRelationshipUserKey, terminationDetails, actionList);
    //    }
    //    catch (ServiceException e)
    //    {
    //      e.printStackTrace();
    //    }
    //    //        WSEndpointReference replyTo =
    //    //            new WSEndpointReference("http://<replace with the URL of the callback service>", WS_ADDR_VER);
    //    //        String uuid = "uuid:" + UUID.randomUUID();
    //    //
    //    //        wsbp.setOutboundHeaders(new StringHeader(WS_ADDR_VER.messageIDTag, uuid),
    //    //                                replyTo.createHeader(WS_ADDR_VER.replyToTag));
    //    return sb;
    //  }
}
