package project.martin.bepeakedprojekt.Misc;

import java.util.ArrayList;
import java.util.Arrays;

import project.martin.bepeakedprojekt.Exercises.ExerciseElement;
import project.martin.bepeakedprojekt.Workout.WorkoutElement;

/**
 * Created by Lasse on 05-12-2016.
 */
public class DummyData {
    public static final ExerciseElement[] exerciseList = {
            new ExerciseElement(1,3,"2-3","Rear Deltoid Row Dumbbell",
                    "1. Place your right foot on the floor, rest your left knee and hand on a flat bench.\n" +
                    "2. Lean forward so your body’s weight is supported by your left arm and knee.\n" +
                    "3. Keeping your back flat, reach down and pick up the dumbbell with your right hand.\n" +
                    "4. Raise your right arm as close to your chest as possible; while bending and bringing your elbow back as far as you can.\n" +
                    "5. Pause at the top for a moment and then lower the dumbbell in a controlled manner to the starting position.\n" +
                    "6. Switch arms by doing the opposite positioning.\n" +
                    "7. Repeat.", 11),
            new ExerciseElement(2,3,"3-4","Bench Press",
                    "1. Lie on a flat bench with your feet flat on the floor, keep your back flat on the bench.\n" +
                    "2. Grasp the bar a little wider than shoulder width apart.\n" +
                    "3. Raise the barbell above your body and move it over the middle of your chest, this is your starting position.\n" +
                    "4. Lower the bar down so it just touches your chest.\n" +
                    "5, Raise the bar till your arms are fully extended and your elbows are locked.\n" +
                    "6. Return to starting position.", 15),
            new ExerciseElement(3,3,"5-6","Hammer Grip Incline Bench Press",
                    "1. Grasp a dumbbell in each hand and lay on an incline bench set at a 45 degree angle.\n" +
                    "2. Keep your feet flat on the floor and your back against the bench at all times.\n" +
                    "3. Using a hammer grip (with your palms facing each other), lift the weights to shoulder height on either side of your chest.\n" +
                    "4. Extend your arms fully and press the dumbbells up.\n" +
                    "5. Slowly return the dumbbells to the starting position at the sides of your chest.", 16),
            new ExerciseElement(4,3,"6-7","Dumbbell Incline Bench Press",
                    "1. Lie on an incline bench which has been set to an incline of 45 degrees.\n" +
                    "2. Start with the dumbbells at shoulder height, your arms wide and elbows pointing down to the floor.\n" +
                    "3. Grasp the dumbbells with a grip so your palms face each other.\n" +
                    "4. Raise your arms up over your chest bringing the dumbbells closer together as they meet over your chest, as if you were clapping.\n" +
                    "5. Slowly return the dumbbells to starting position.", 16),
            new ExerciseElement(5,3,"7-8","Incline Shoulder Press Dumbbell",
                    "1. Lie down on an incline bench with your feet flat on the floor and grasp the dumbbells.\n" +
                    "2. With your elbows bent raise your arms up until in line with your shoulders this is your starting position.\n" +
                    "3. With your abs drawn in, raise the dumbbells as high as you are able above your shoulders.\n" +
                    "4. Lower the dumbbells in a slow controlled manner to starting position.", 16),
            new ExerciseElement(6,3,"8-9","Decline Barbell Bench Press",
                    "1. Lie on a decline bench with your head lower than your feet.\n" +
                    "2. Grasp the bar at a grip 3-6 inches wider than your shoulders.\n" +
                    "3. Raise the bar above your chest, keeping your elbows close in.\n" +
                    "4. Slowly and with control lower the bar straight to your lower chest.\n" +
                    "5. Raise the bar back up to starting position with the bar just above your chest.\n" +
                    "6. Repeat..", 17),
            new ExerciseElement(7,3,"9-10","Lateral Dumbbell Raises",
                    "1. Grasp a dumbbell in each hand, palms facing inward towards your body and the dumbbells at your sides.\n" +
                    "2. Standing with your feet shoulder with apart, draw your abs in and bend your knees slightly.\n" +
                    "3. While keeping your torso still, raise the your arms up to the sides keeping your arms straight with a slight bend in the elbows.\n" +
                    "4. Raise your arms up until in line with your shoulders, your palms should face the floor.\n" +
                    "5. Hold the position for a moment then in a controlled movement lower your arms to the starting position.\n" +
                    "6. Repeat", 20),
            new ExerciseElement(8,3,"10-11","Lying Leg Curl Machine",
                    "1. Lie face down on a leg curl machine and place your heels under the roller pad.\n" +
                    "2. Grasp the grips with your hands for support and slowly curl your ankles up towards your back.\n" +
                    "3. Hold for a moment and then return to starting position.\n" +
                    "4. Repeat.", 22),
            new ExerciseElement(9,3,"11-12","Bicep Curls with Barbell",
                    "1. Stand with your feet shoulder width apart, your knees slightly bent and your abs drawn in.\n" +
                    "2. Grasp a barbell with palms facing up, approximately shoulder width apart.\n" +
                    "3. Lower your arms fully to above your thighs and bending only your elbows, raise the bar to your upper chest.\n" +
                    "4. Pause for a moment and then return to the starting position.", 24),
            new ExerciseElement(10,3,"12-13","Tricep Dips using Body Weight",
                    "1. Grasp a set of parallel bars and push yourself up to the starting position.\n" +
                    "2. Keeping your elbows close to your body, lower yourself down until your triceps are parallel to the floor.\n" +
                    "3. Slowly raise yourself back to the starting position.", 29)
    };

    public static ExerciseElement getExercise(int exerciseID) {
        for (ExerciseElement exercise : exerciseList) {
            if(exercise.getExerciseID() == exerciseID)
                return exercise;
        }
        return null;
    }

    public static final WorkoutElement[] workoutList = {
            new WorkoutElement(1, "Bench", new ArrayList<>(Arrays.asList(getExercise(2), getExercise(3), getExercise(4), getExercise(6)))),
            new WorkoutElement(2, "Dumbell", new ArrayList<>(Arrays.asList(getExercise(1), getExercise(4), getExercise(5), getExercise(7)))),
            new WorkoutElement(3, "Curl", new ArrayList<>(Arrays.asList(getExercise(8), getExercise(9)))),
            new WorkoutElement(4, "All", new ArrayList<>(Arrays.asList(exerciseList)))
    };
}