package core;

import core.display.EngineFrame;

public class Handler {
    private Engine
            Engine;

    public Handler(Engine HANDLER_ENGINE) {
        this.Engine = HANDLER_ENGINE;
    }
    public Engine getEngine() {
        return Engine;
    }
    public EngineFrame getDisplay() {
        return Engine.getDisplay();
    }
}
