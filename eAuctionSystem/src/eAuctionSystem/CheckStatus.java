package eAuctionSystem;

import java.time.LocalDateTime;
import java.util.List;

/*
 * This class is used to check for expired auctions
 */
public class CheckStatus implements Runnable {
private List<Auction> auctions;	
private Integer delay;


    public CheckStatus(List<Auction> auctions, Integer seconds) {
    	this.auctions=auctions;
    	setSeconds(seconds);
   
    
    }

	@Override
	public void run() {
		
		while(true) {
			
				try {
					Thread.sleep(delay);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
				synchronized(auctions) {
					for(Auction auction: auctions) {
						if(auction.getStatus() == Status.STATUS_ACTIVE) {
							// if auction is not active and has expired, close the auction
							if(auction.getCloseDate().isBefore(LocalDateTime.now())) {
								auction.close();
							}
							
							
						}
						
						
					}
					
				}
				
		}
							
				
		}
		

				
	public synchronized void setSeconds(Integer seconds) {
		delay = seconds * 1000;
		
	}
	
	
		
	}
			
		
	

		
		
	


