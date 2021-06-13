package ir.mohammadhf.painterkid.feature.main;

import java.util.List;

import ir.mohammadhf.painterkid.base.BaseViewModel;
import ir.mohammadhf.painterkid.data.model.PaintPackage;
import ir.mohammadhf.painterkid.feature.iapurchase.PurchaseHandler;
import ir.mohammadhf.painterkid.utils.DataFakeGenerator;

public class MainViewModel extends BaseViewModel {
    private List<PaintPackage> paintPackageList;

    private int currentSelectedId;

    public void loadData() {
        if (paintPackageList == null) {
            paintPackageList = DataFakeGenerator.getPackageModels();
            if (PurchaseHandler.isPremiumAccount()) {
                for (PaintPackage paintPackage :
                        paintPackageList) {
                    if (paintPackage.isLocked())
                        paintPackage.setLocked(false);
                }
            } else {
                //todo: Add any string has added to packages here.
                for (PaintPackage paintPackage :
                        paintPackageList) {
                    if (paintPackage.getId() == DataFakeGenerator.PACKAGE_PRINCESS_ID) {
                        paintPackage.setLocked(!PurchaseHandler.isPackagePrincessPurchased());
                    } else if (paintPackage.getId() == DataFakeGenerator.PACKAGE_FOOD_ID) {
                        paintPackage.setLocked(!PurchaseHandler.isPackageFoodPurchased());
                    } else if (paintPackage.getId() == DataFakeGenerator.PACKAGE_ANIMATION_CHARACTER_ID) {
                        paintPackage.setLocked(!PurchaseHandler.isPackageAnimationCharacterPurchased());
                    } else if (paintPackage.getId() == DataFakeGenerator.PACKAGE_VEHICLE_ID) {
                        paintPackage.setLocked(!PurchaseHandler.isPackageTransportPurchased());
                    }
                }
            }
        }
    }

    public PaintPackage getPackageAtPos(int pos) {
        return paintPackageList != null ? paintPackageList.get(pos) : null;
    }

    public void clearData() {
        paintPackageList = null;
    }

    public int getCurrentSelectedId() {
        return currentSelectedId;
    }

    public void setCurrentSelectedId(int currentSelectedId) {
        if (currentSelectedId >= paintPackageList.size())
            currentSelectedId = 0;
        else if (currentSelectedId < 0)
            currentSelectedId = paintPackageList.size() - 1;
        this.currentSelectedId = currentSelectedId;
    }
}
