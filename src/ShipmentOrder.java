public abstract class ShipmentOrder implements SummaryPrintable {
    private String orderNumber;
    private String customerName;
    private double distanceKm;
    private double baseFee;
    private boolean insured;
    protected double lastCalculatedPrice;

    public ShipmentOrder(String orderNumber, String customerName, double distanceKm, double baseFee, boolean insured) {
        this.orderNumber = orderNumber;
        this.customerName = customerName;
        this.distanceKm = distanceKm;
        this.baseFee = baseFee;
        this.insured = insured;
    }

    public String getOrderNumber() {
        return orderNumber;
    }

    public String getCustomerName() {
        return customerName;
    }

    public double getDistanceKm() {
        return distanceKm;
    }

    public double getBaseFee() {
        return baseFee;
    }

    public boolean isInsured() {
        return insured;
    }

    public double getLastCalculatedPrice() {
        return lastCalculatedPrice;
    }

    public final void processOrder() {
        validateOrder();
        validateSpecificRules();

        double price = calculateBasePrice();
        price += calculateAdditionalFee();
        price = applyInsurance(price);
        price = applyBusinessDiscount(price);

        lastCalculatedPrice = price;
        printProcessingResult();
    }

    private void validateOrder() {
        if (orderNumber == null || orderNumber.isBlank()) {
            throw new IllegalArgumentException("Order number cannot be empty.");
        }
        if (customerName == null || customerName.isBlank()) {
            throw new IllegalArgumentException("Customer name cannot be empty.");
        }
        if (distanceKm <= 0) {
            throw new IllegalArgumentException("Distance must be greater than 0.");
        }
        if (baseFee < 0) {
            throw new IllegalArgumentException("Base fee cannot be negative.");
        }
    }

    protected void validateSpecificRules() {
    }

    private double applyInsurance(double price) {
        if (insured) {
            price += price * 0.07;
        }

        return price;
    }

    protected double applyBusinessDiscount(double price) {
        return price;
    }

    private void printProcessingResult() {
        System.out.println("Processed order: " + orderNumber
                + " Type: " + getShipmentType()
                + " Final price: " + String.format("%.2f PLN", lastCalculatedPrice));
    }

    @Override
    public String buildSummaryLine() {
        return "Order: " + orderNumber
                + " Customer: " + customerName
                + " Shipment type: " + getShipmentType()
                + " Last calculated price: "
                + String.format("%.2f PLN", lastCalculatedPrice);
    }

    protected abstract double calculateBasePrice();

    protected abstract double calculateAdditionalFee();

    public abstract String getShipmentType();
}