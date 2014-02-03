package eu.tjenwellens.lessenroosterchecker.compare;

import java.util.Collection;

/**
 *
 * @author Tjen
 */
public class Waiter extends Thread {
    private Comparer comp;
    private Collection<KlassenHolder> holders;

    public Waiter(Comparer comp) {
        this.comp = comp;
    }

    public void setHolders(Collection<KlassenHolder> holders) {
        this.holders = holders;
    }

    @Override
    public void run() {
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
        if (holders == null) {
            return true;
        }
        for (KlassenHolder klassenHolder : holders) {
            if (!klassenHolder.isReady()) {
                return false;
            }
        }
        return true;
    }
}
