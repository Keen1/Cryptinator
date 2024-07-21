package controllers;

import cryptinators.Cryptinator;
import cryptinators.Encryptinator;
import javax.crypto.SecretKey;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class EncrypterController extends Controller{

    public EncrypterController(SecretKey key, List<String> paths){
        super(key, paths);
        this.setMode("encrypt");
    }

    @Override
    public void execute(){
        Cryptinator cryptinator = null;
        try(ExecutorService executor = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors())){
            for(String path: this.getPaths()){
                cryptinator = new Encryptinator(path, this.getKey());
                executor.submit(cryptinator);
            }
        }
    }
}
