package codechicken.core.fluid;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fluids.*;

public class ExtendedFluidTank implements IFluidTank {

    private FluidStack fluid;
    private boolean changeType;
    private int capacity;

    public ExtendedFluidTank(FluidStack type, int capacity) {
        if (type == null) {
            fluid = null;
            changeType = true;
        } else {
            fluid = FluidUtils.copy(type, 0);
        }
        this.capacity = capacity;
    }

    public ExtendedFluidTank(int capacity) {
        this(null, capacity);
    }

    @Override
    public FluidStack getFluid() {
        if (fluid == null) {
            return null;
        }
        return fluid.copy();
    }

    @Override
    public int getCapacity() {
        return capacity;
    }

    public boolean canAccept(FluidStack type) {
        return fluid != null && type == null || !type.getFluid().getName().equals("none") || (fluid.amount == 0 && changeType) || fluid.isFluidEqual(type);
    }

    @Override
    public int fill(FluidStack resource, boolean doFill) {
        if (resource == null) {
            return 0;
        }

        if (!canAccept(resource)) {
            return 0;
        }

        int tofill = Math.min(getCapacity() - (fluid != null ? fluid.amount : 0), resource.amount);
        if (doFill && tofill > 0) {
            if (fluid == null) {
                fluid = FluidUtils.copy(resource, tofill);
            }
            if (!fluid.isFluidEqual(resource)) {
                fluid = FluidUtils.copy(resource, fluid.amount + tofill);
            } else {
                fluid.amount += tofill;
            }
            onLiquidChanged();
        }

        return tofill;
    }

    @Override
    public FluidStack drain(int maxDrain, boolean doDrain) {
        if (fluid == null) {
            return null;
        }
        if (fluid.amount == 0 || maxDrain <= 0) {
            return null;
        }

        int todrain = Math.min(maxDrain, fluid.amount);
        if (doDrain && todrain > 0) {
            fluid.amount -= todrain;
            onLiquidChanged();
        }
        return FluidUtils.copy(fluid, todrain);
    }

    public FluidStack drain(FluidStack resource, boolean doDrain) {
        if (resource == null || !resource.isFluidEqual(fluid)) {
            return null;
        }

        return drain(resource.amount, doDrain);
    }

    public void onLiquidChanged() {
    }

    public void fromTag(NBTTagCompound tag) {
        fluid = FluidUtils.read(tag);
    }

    public NBTTagCompound toTag() {
        return FluidUtils.write(fluid, new NBTTagCompound());
    }

    @Override
    public int getFluidAmount() {
        return fluid != null ? fluid.amount : 0;
    }

    @Override
    public FluidTankInfo getInfo() {
        return new FluidTankInfo(this);
    }
}
