public class PickupPointShipment extends ShipmentOrder {
    private String lockerSize;
    private boolean fragile;

    public PickupPointShipment(String orderNumber, String customerName, double distanceKm, double baseFee, boolean insured, String lockerSize, boolean fragile) {
        super(orderNumber, customerName, distanceKm, baseFee, insured);
        this.lockerSize = lockerSize;
        this.fragile = fragile;
    }

    @Override
    protected void validateSpecificRules() {
        if (!lockerSize.equals("S")
                && !lockerSize.equals("M")
                && !lockerSize.equals("L")) {

            throw new IllegalArgumentException("Invalid locker size.");
        }
    }

    @Override
    protected double calculateBasePrice() {
        return getBaseFee() + getDistanceKm() * 0.75;
    }

    @Override
    protected double calculateAdditionalFee() {
        double additionalFee = 0;
        switch (lockerSize) {
            case "S":
                additionalFee += 5;
                break;
            case "M":
                additionalFee += 10;
                break;
            case "L":
                additionalFee += 18;
                break;
        }
        if (fragile) {
            additionalFee += 12;
        }
        return additionalFee;
    }

    @Override
    public String getShipmentType() {
        return "Pickup point";
    }
}