package aaa;

public class DNA {

    //Physics
    public float maxSpeed;
    public float maxForce;
    // Vision
    public float visionDistance;
    public float visionSafeDistance;
    public float visionAngle;
    // Pursuit
    public float deltaTPursuit;
    // Arrive
    public float radiusArrive;
    // Wander
    public float deltaTWander;
    public float radiusWander;
    public float deltaPhiWander;

    public DNA() {
        // Physics
        maxSpeed = random(1f, 2f);
        maxForce = random(4f, 7f);
        // Vision
        visionDistance = random(1.5f, 2.5f);
        visionSafeDistance = 0.25f * visionDistance;
        visionAngle = (float) Math.PI * 0.3f;
        // Pursuit
        deltaTPursuit = random(0.5f, 1f);
        // Arrive
        radiusArrive = random(3, 5);
        // Wander
        deltaTWander = random(0.5f, 0.7f);
        radiusWander = random(2, 4);
        deltaPhiWander = (float) Math.PI / 8;
    }

    public DNA(DNA dna, boolean mutate) {
        maxSpeed = dna.maxSpeed;
        maxForce = dna.maxForce;

        visionDistance = dna.visionDistance;
        visionSafeDistance = dna.visionSafeDistance;
        visionAngle = dna.visionAngle;
        // Pursuit
        deltaTPursuit = dna.deltaTPursuit;
        // Arrive
        radiusArrive = dna.radiusArrive;
        // Wander
        deltaTWander = dna.deltaTWander;
        radiusWander = dna.radiusWander;
        deltaPhiWander = dna.deltaPhiWander;
        if (mutate) mutate();
    }

    private void mutate() {
        maxSpeed += random(-0.2f, 0.2f);
        maxSpeed = Math.max(0, maxSpeed);
    }

    public static float random(float min, float max) {
        return (float) (min + (max - min) * Math.random());
    }

}
