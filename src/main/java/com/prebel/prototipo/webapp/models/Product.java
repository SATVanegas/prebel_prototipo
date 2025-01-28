package com.prebel.prototipo.webapp.models;

public class Product {


    private String reference;
    private String packagingType; // to define if it remains as a java enum
    private String packagingMaterial; // to define if it remains as a java enum
    private String containerColor; // to define if it remains as a java enum
    private String lidMaterial; // to define if it remains as a java enum
    private String lidColor; // to define if it remains as a java enum
    private int batch;

    public Product(String reference, String packagingType, String packagingMaterial, String containerColor, String lidMaterial, String lidColor, int batch) {
        this.reference = reference;
        this.packagingType = packagingType;
        this.packagingMaterial = packagingMaterial;
        this.containerColor = containerColor;
        this.lidMaterial = lidMaterial;
        this.lidColor = lidColor;
        this.batch = batch;
    }


    public String getReference() {
        return reference;
    }

    public void setReference(String reference) {
        this.reference = reference;
    }

    public String getPackagingType() {
        return packagingType;
    }

    public void setPackagingType(String packagingType) {
        this.packagingType = packagingType;
    }

    public String getPackagingMaterial() {
        return packagingMaterial;
    }

    public void setPackagingMaterial(String packagingMaterial) {
        this.packagingMaterial = packagingMaterial;
    }

    public String getContainerColor() {
        return containerColor;
    }

    public void setContainerColor(String containerColor) {
        this.containerColor = containerColor;
    }

    public String getLidMaterial() {
        return lidMaterial;
    }

    public void setLidMaterial(String lidMaterial) {
        this.lidMaterial = lidMaterial;
    }

    public String getLidColor() {
        return lidColor;
    }

    public void setLidColor(String lidColor) {
        this.lidColor = lidColor;
    }

    public int getBatch() {
        return batch;
    }

    public void setBatch(int batch) {
        this.batch = batch;
    }

    @Override
    public String toString() {
        return "Product{" +
                "reference='" + reference + '\'' +
                ", packagingType='" + packagingType + '\'' +
                ", packagingMaterial='" + packagingMaterial + '\'' +
                ", containerColor='" + containerColor + '\'' +
                ", lidMaterial='" + lidMaterial + '\'' +
                ", lidColor='" + lidColor + '\'' +
                ", batch=" + batch +
                '}';
    }
}
