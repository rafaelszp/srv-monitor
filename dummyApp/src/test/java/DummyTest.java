
import org.apache.log4j.Logger;
import org.junit.Test;


/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author rafael
 */
public class DummyTest {
    
    Logger log = Logger.getLogger(DummyTest.class);
    
    
    public Exception getException(){
        Exception e;
        try{
            throw new Exception();
        }catch(Exception ex){
            e = ex;
        }
        return e;
    }
    
    @Test
    public void shoudLogInfo(){
        log.info("called shouldLogInfo");
    }
    
    @Test
    public void shoudLogWarn(){
        log.warn("called shouldLogWarn",getException());
        log.warn(getException());
        log.warn(null);
    }
    
    @Test
    public void shoudLogError(){
        log.error("called shouldLogError",getException());
    }
    
    @Test
    public void shoudLogDebug(){
        log.debug("called shouldLogDebug");
    }
}
