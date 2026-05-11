public class InternationalShipment extends ShipmentOrder {
    private String destinationCountry;
    private boolean customsDocumentsRequired;
    private boolean expressDelivery;

    public InternationalShipment(String orderNumber, String customerName, double distanceKm, double baseFee, boolean insured, String destinationCountry, boolean customsDocumentsRequired, boolean expressDelivery) {
        super(orderNumber, customerName, distanceKm, baseFee, insured);
        this.destinationCountry = destinationCountry;
        this.customsDocumentsRequired = customsDocumentsRequired;
        this.expressDelivery = expressDelivery;
    }

    @Override
    protected void validateSpecificRules() {
        if (destinationCountry == null || destinationCountry.isBlank()) {
            throw new IllegalArgumentException("Destination country cannot be empty.");
        }
    }

    @Override
    protected double calculateBasePrice() {
        return getBaseFee() + getDistanceKm() * 2.10;
    }

    @Override
    protected double calculateAdditionalFee() {
        double additionalFee = 0;
        if (customsDocumentsRequired) {
            additionalFee += 45;
        }
        if (expressDelivery) {
            additionalFee += 80;
        }
        return additionalFee;
    }

    @Override
    protected double applyBusinessDiscount(double price) {
        if (!expressDelivery && getDistanceKm() > 1000) {
            price -= price * 0.03;
        }
        return price;
    }

    @Override
    public String getShipmentType() {
        return "International";
    }
}