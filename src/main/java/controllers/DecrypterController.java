package controllers;

import cryptinators.Cryptinator;
import cryptinators.Decryptinator;
import javax.crypto.SecretKey;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class DecrypterController extends Controller{

    public DecrypterController( SecretKey key, List<String> paths){
        super(key, paths);
        this.setMode("decrypt");
    }
    @Override
    public void execute(){
        Cryptinator cryptinator;
        try(ExecutorService executor = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors())){
            for(String path: this.getPaths()){
                cryptinator = new Decryptinator(path, this.getKey());
                executor.submit(cryptinator);
            }
        }

    }
}
