package bean;

//import com.octetstring.vde.operation.Operation;

import oracle.adf.model.BindingContext;
import oracle.adf.view.rich.event.PopupFetchEvent;
import oracle.adf.model.OperationBinding;
import oracle.binding.BindingContainer;
import oracle.adf.view.rich.event.DialogEvent;
import oracle.adf.view.rich.event.PopupCanceledEvent;


public class SHRUTI_PolicyBean {
    

    public SHRUTI_PolicyBean() {
        super();
    }
    public void editPopupFetchListener(PopupFetchEvent popupFetchEvent)
    {
        if(popupFetchEvent.getLaunchSourceClientId().contains("cb4"))
        {BindingContainer bindings= getBindings();
         OperationBinding operationbinding=(OperationBinding)bindings.getOperationBinding("createInsert");
           operationbinding.execute(); 
            }
        
        }
    
    
    
    public void editDialogListener(DialogEvent dialogEvent) 
    {
        if(dialogEvent.getOutcome().name().equals("ok"))
        {BindingContainer bindings=getBindings();
         OperationBinding operationbinding=(OperationBinding)bindings.getOperationBinding("commit");
         operationbinding.execute();
            }
        else if(dialogEvent.getOutcome().name().equals("cancel"))
            {
                BindingContainer bindings=getBindings();
             OperationBinding operationbinding=(OperationBinding)bindings.getOperationBinding("RollBack");
                    operationbinding.execute();
                                                
                }
        }
    
    public void editPopupCancelListener(PopupCanceledEvent popupCanceledEvent)
    {
        BindingContainer bindings=getBindings();
        OperationBinding operationbinding=(OperationBinding)bindings.getOperationBinding("RollBack");
        operationbinding.execute();
        
        }

    private BindingContainer getBindings() {
        return BindingContext.getCurrent().getCurrentBindingsEntry();
    }
}
