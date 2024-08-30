package pl.zabel.lank;

import java.util.Timer;

import pl.zabel.lank.utilities.maputilities.MovingTask;

public class ScheduledMovementTimer implements Runnable{

	private Timer timer;
	
	@Override
	public void run() {
		
		timer = new Timer();		
	}
	
	public void scheduleMovementTask(MovingTask task)
	{
		timer.schedule(task,(long)1, 30);
	}
	public void cancelTimer()
	{
		timer.cancel();
	}
}
