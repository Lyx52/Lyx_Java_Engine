package core.state;

import core.Handler;

public abstract class EngineState {
    public Handler
            handler;

    public EngineState(Handler handler) {
        this.handler = handler;
    }
    public abstract void OnRender();
    public abstract void OnTick();
}
