package model.services;

import model.entities.CarRental;
import model.entities.Invoice;

public class RentalService {

    private Double pricePerHour;
    private Double pricePerDay;

    private BrazilTaxService taxService;

    public RentalService(Double pricePerHour, Double pricePerDay) {
        taxService = new BrazilTaxService();
        this.pricePerHour = pricePerHour;
        this.pricePerDay = pricePerDay;
    }

    public void processInvoice(CarRental carRental) {
        long diff = carRental.getFinish().getTime() - carRental.getStart().getTime();
        double hours = (double) diff / 1000 / 60 / 60;

        double basicPayment = (hours <= 12) ? Math.ceil(hours) * pricePerHour : Math.ceil(hours / 24) * pricePerDay;

        double tax = taxService.tax(basicPayment);
        carRental.setInvoice(new Invoice(basicPayment, tax));
    }
}
