package payments.model;

public final class BankAccountHolder {

    public BankAccountHolder(int id, String firstName, String lastName, String email){
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
    }

    public int getClientId() {
        return id;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getEmail() {
        return email;
    }

    public boolean equals(Object other){
        if (this == other) return true;
        if (other == null) return false;
        if (! (other instanceof BankAccountHolder)) return false;
        if (this.id != ((BankAccountHolder) other).getClientId()) return false;
        return this.email.compareToIgnoreCase(((BankAccountHolder) other).getEmail()) == 0;
    }

    @Override
    public int hashCode(){
        return id*31 + this.email.toLowerCase().hashCode();
    }

    private final int id;
    private final String firstName;
    private final String lastName;
    private final String email;
}
