package bean;

import oracle.adf.model.BindingContext;
import oracle.adf.model.OperationBinding;
import oracle.adf.view.rich.event.DialogEvent;
import oracle.adf.view.rich.event.PopupCanceledEvent;
import oracle.adf.view.rich.event.PopupFetchEvent;

import oracle.binding.BindingContainer;

public class PolicyViolationBean {
    public PolicyViolationBean() {
        super();
    }
    public void editPopupFetchListener(PopupFetchEvent popupFetchEvent) {
        if(popupFetchEvent.getLaunchSourceClientId().contains("cbAdd"))
        {
            BindingContainer bindings= getBindings();
            OperationBinding operationBinding;
            operationBinding = (OperationBinding)bindings.getOperationBinding("CreateInsert");
            operationBinding.execute();
            
        }
    }
        public void editDialogListener(DialogEvent dialogEvent) {
            if(dialogEvent.getOutcome().name().equals("ok")) {
                BindingContainer bindings=getBindings();
                OperationBinding operationBinding = (OperationBinding)bindings.getOperationBinding("Commit");
                operationBinding.execute();
                
            }
            
        }
        
    public void editPopupCancelListener(PopupCanceledEvent popupCanceledEvent) {
       
            BindingContainer bindings=getBindings();
            OperationBinding operationBinding = (OperationBinding)bindings.getOperationBinding("Rollback");
            operationBinding.execute();
            
        
        
    }    
    

    public BindingContainer getBindings() {
        return BindingContext.getCurrent().getCurrentBindingsEntry();
    }
}
