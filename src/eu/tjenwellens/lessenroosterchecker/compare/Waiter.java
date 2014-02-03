package eu.tjenwellens.lessenroosterchecker.compare;

import java.util.Collection;

/**
 *
 * @author Tjen
 */
public class Waiter extends Thread {
    private WaiterWaiter comp;
    private Collection<Jaar> jaren;

    public Waiter(WaiterWaiter comp) {
        this.comp = comp;
    }

    public void setHolders(Collection<Jaar> holders) {
        this.jaren = holders;
    }

    @Override
    public void run() {
        for (Jaar jaar : jaren) {
            jaar.start();
        }
        synchronized (this) {
            while (!areAllDone()) {
                try {
                    wait();
                } catch (InterruptedException e) {
                }
            }
            comp.doneWaiting();
        }
    }

    private boolean areAllDone() {
        if (jaren == null) {
            return true;
        }
        for (Jaar klassenHolder : jaren) {
            if (!klassenHolder.isReady()) {
                return false;
            }
        }
        return true;
    }
}
