package pl.zabel.lank;

import java.util.Timer;
import java.util.TimerTask;

import pl.zabel.lank.utilities.maputilities.MovingTask;

public class ScheduledAnimationTimer implements Runnable{

		private Timer timer;
		
		@Override
		public void run() {
			
			timer = new Timer();		
		}
		
		public void scheduleAnimationTask(TimerTask task)
		{
			timer.schedule(task,(long)1, 100);
		}
		public void cancelTimer()
		{
			timer.cancel();
		}
}
