package POJO;

import java.util.List;

public class AutorizationPOST {
    private String buyerDocument;
    private List<Authorization> authorizations;

    public String getBuyerDocument() {
        return buyerDocument;
    }

    public void setBuyerDocument(String buyerDocument) {
        this.buyerDocument = buyerDocument;
    }

    public List<Authorization> getAuthorizations() {
        return authorizations;
    }

    public void setAuthorizations(List<Authorization> authorizations) {
        this.authorizations = authorizations;
    }

    public static class Authorization {
        private String sellerDocument;
        private double amount;
        private boolean isSplit;

        public String getSellerDocument() {
            return sellerDocument;
        }

        public void setSellerDocument(String sellerDocument) {
            this.sellerDocument = sellerDocument;
        }

        public double getAmount() {
            return amount;
        }

        public void setAmount(double amount) {
            this.amount = amount;
        }

        public boolean isSplit() {
            return isSplit;
        }

        public void setSplit(boolean split) {
            isSplit = split;
        }
    }
    }