package model;

public final class BankClient implements BankAccountHolder, Comparable<BankClient> {

    public BankClient( int id, String firstName, String lastName, String email){
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
    }

    @Override
    public int getClientId() {
        return id;
    }

    @Override
    public String getFirstName() {
        return firstName;
    }

    @Override
    public String getLastName() {
        return lastName;
    }

    @Override
    public String getEmail() {
        return email;
    }

    @Override
    public boolean equals(Object other){
        if (this == other) return true;
        if (other == null) return false;
        if (! (other instanceof BankClient)) return false;
        if (this.id != ((BankClient) other).getClientId()) return false;
        if (this.email.compareToIgnoreCase(((BankClient) other).getEmail())!=0) return false;

        return true;
    }

    @Override
    public int hashCode(){
        return id*31 + this.email.toLowerCase().hashCode();
    }

    private final int id;
    private final String firstName;
    private final String lastName;
    private final String email;

    @Override
    public int compareTo(BankClient that) {
        return this.getClientId() - that.getClientId();
    }
}
