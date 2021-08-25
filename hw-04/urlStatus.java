public class urlStatus {
    private String url;
    private String status;

    public urlStatus(String url, String status){
        this.url = url;
        this.status = status;
    }


    public String getUrl(){
        return url;
    }

    public void setStatus(String status){
        this.status = status;
    }


    public String getStatus(){
        return status;
    }



}
