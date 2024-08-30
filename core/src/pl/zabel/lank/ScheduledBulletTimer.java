package pl.zabel.lank;

import java.util.Timer;
import java.util.TimerTask;

public class ScheduledBulletTimer implements Runnable{

		private Timer timer;
		
		@Override
		public void run() {
			
			timer = new Timer();		
		}
		
		public void scheduleBulletTask(TimerTask task)
		{
			timer.schedule(task,(long)1, 30);
		}
		public void cancelTimer()
		{
			timer.cancel();
		}
}
