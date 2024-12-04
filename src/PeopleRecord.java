public class PeopleRecord implements Comparable<PeopleRecord> {
    private String givenName;
    private String familyName;
    private String companyName;
    private String address;
    private String city;
    private String county;
    private String state;
    private String zip;
    private String phone1;
    private String phone2;
    private String email;
    private String web;
    private String birthday;

    public PeopleRecord(String line){
        String[] fields = line.split(";");
        this.givenName = fields[0];
        this.familyName = fields[1];
        this.companyName = fields[2];
        this.address = fields[3];
        this.city = fields[4];
        this.county = fields[5];
        this.state = fields[6];
        this.zip = fields[7];
        this.phone1 = fields[8];
        this.phone2 = fields[9];
        this.email = fields[10];
        this.web = fields[11];
        this.birthday = fields[12];
    }

    @Override
    public String toString() {
        return givenName + " " + familyName + " (" + email + ")";
    }
    //All getters
    public String getGivenName() {
        return givenName;
    }
    public String getFamilyName() {
        return familyName;
    }
    public String getCompanyName() {
        return companyName;
    }
    public String getAddress() {
        return address;
    }
    public String getCity() {
        return city;
    }
    public String getCounty() {
        return county;
    }
    public String getState() {
        return state;
    }
    public String getZip() {
        return zip;
    }
    public String getPhone1() {
        return phone1;
    }
    public String getPhone2() {
        return phone2;
    }
    public String getEmail() {
        return email;
    }
    public String getWeb() {
        return web;
    }
    public String getBirthday() {
        return birthday;
    }

    @Override
    public int compareTo(PeopleRecord o) {
        if (this.getGivenName().compareTo(o.getGivenName())>0){
            return 1;
        }
        else if (this.getGivenName().compareTo(o.getGivenName())<0){
            return -1;
        }
        return 0;
    }
}
