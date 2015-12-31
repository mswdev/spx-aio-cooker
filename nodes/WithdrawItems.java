package scripts.SPXAIOCooker.nodes;

import org.tribot.api.General;
import org.tribot.api.Timing;
import org.tribot.api.types.generic.Condition;
import org.tribot.api2007.Banking;
import org.tribot.api2007.Inventory;
import org.tribot.api2007.WebWalking;
import scripts.SPXAIOCooker.Variables;
import scripts.SPXAIOCooker.api.Node;

/**
 * Created by Sphiinx on 12/26/2015.
 */
public class WithdrawItems extends Node {

    public WithdrawItems(Variables v) {
        super(v);
    }

    @Override
    public void execute() {
        if (Banking.isInBank()) {
            if (Banking.isBankScreenOpen()) {
                withdrawItems();
            } else {
                openBank();
            }
        } else {
            walkToBank();
        }
    }

    public void withdrawItems() {
        if (Banking.find(vars.foodId).length > 0) {
            if (Banking.withdraw(28, vars.foodId)) {
                Timing.waitCondition(new Condition() {
                    @Override
                    public boolean active() {
                        return Inventory.getCount(vars.foodId) == 28;
                    }
                }, General.random(750, 1000));
            }
        } else {
            General.println("We could not find the food requested...");
            General.println("Stopping Script...");
            vars.stopScript = true;
        }
    }

    public void openBank() {
        if (Banking.openBank()) {
            Timing.waitCondition(new Condition() {
                @Override
                public boolean active() {
                    return Banking.isBankScreenOpen();
                }
            }, General.random(750, 1000));
        }
    }

    private void walkToBank() {
        if (WebWalking.walkToBank()) {
            Timing.waitCondition(new Condition() {
                @Override
                public boolean active() {
                    return Banking.isInBank();
                }
            }, General.random(750, 1000));
        }
    }

    @Override
    public boolean validate() {
        return Inventory.getCount(vars.foodId) <= 0;
    }

}
