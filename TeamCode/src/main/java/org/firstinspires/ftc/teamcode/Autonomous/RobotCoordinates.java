package org.firstinspires.ftc.teamcode.Autonomous;

import java.util.ArrayList;
import java.util.Arrays;

public class RobotCoordinates {

    // if headings don't work switch to imu and gyro << implies changing how we initialize

    // -+-+-+-+-+-+-+-+- UNLESS STATED OTHERWISE POSITION FOR BLUE ALLIANCE
    // -+-+-+-+-+-+-+-+- FOR RED FLIP SIGN OF Y POSITION AND OF HEADING
    // -+-+-+-+-+-+-+-+- heading change doesn't work for 0 to +-180 headings or vice versa but that shouldn't matter

    // -----------------starting positions
    public static final ArrayList<Integer> start_1blue = new ArrayList<>(Arrays.asList(-32, 75, -90));
    public static final ArrayList<Integer> start_1red = new ArrayList<>(Arrays.asList(-32, -75, 90)); // CHANGE
    public static final ArrayList<Integer> start_2 = new ArrayList<>(Arrays.asList(28, 75, 90));

    // -----------------skystones << from left to right based on robot viewpoint
    public static final ArrayList<Integer> stone_one = new ArrayList<>(Arrays.asList(-25, 23, -90));
    public static final ArrayList<Integer> stone_two = new ArrayList<>(Arrays.asList(-35, stone_one.get(1), -90));
    public static final ArrayList<Integer> stone_three = new ArrayList<>(Arrays.asList(-45, stone_one.get(1), -90));
    public static final ArrayList<Integer> stone_four = new ArrayList<>(Arrays.asList(-55, stone_one.get(1), -90));
    public static final ArrayList<Integer> stone_five = new ArrayList<>(Arrays.asList(-65, stone_one.get(1), -90));
    public static final ArrayList<Integer> stone_six = new ArrayList<>(Arrays.asList(-75, stone_one.get(1), -90));

    // -----------------foundation
    public static final ArrayList<Integer> found = new ArrayList<>(Arrays.asList(31, 35, 90));

    // ----------------- position 1 -----------------
    public static final int x = 0; // placeholder for skystone x position

    public static final ArrayList<Integer> one_1blue = new ArrayList<>(Arrays.asList(start_1blue.get(0), start_1blue.get(1) - 15, -90)); // drive forward from wall
    public static final ArrayList<Integer> two_1blue = new ArrayList<>(Arrays.asList(start_1blue.get(0) - 8, one_1blue.get(1), -90)); // strafe right to catch target

    public static final ArrayList<Integer> one_1red = new ArrayList<>(Arrays.asList(start_1red.get(0), start_1red.get(1) + 15, 90)); // drive forward from wall
    // red side doesn't get a two_1red to make everything even :D

    public static final ArrayList<Integer> three_1blue = new ArrayList<>(Arrays.asList(x, one_1blue.get(1), -90)); // strafe to stone X position (X, 60, -90)
    public static final ArrayList<Integer> three_1red = new ArrayList<>(Arrays.asList(x, one_1red.get(1), 90)); // strafe to stone X position (X, 60, -90)

    public static final ArrayList<Integer> four_1 = new ArrayList<>(Arrays.asList(x, stone_one.get(1), -90)); // drive forward to stone position to pick up (X, 23, -90)

    // -----------------position 1a
    public static final ArrayList<Integer> five_1a = new ArrayList<>(Arrays.asList(x, stone_one.get(1) + 5, -90)); // back up from stone to allow for robot to turn (X, 28, -90)
    public static final ArrayList<Integer> six_1a = new ArrayList<>(Arrays.asList(14, five_1a.get(1), 0)); // drive to other side to drop off stone
    public static final ArrayList<Integer> seven_1a = new ArrayList<>(Arrays.asList(x, five_1a.get(1), 0)); // drive to other stone X position backwards (X, 28, 0)
    public static final ArrayList<Integer> eight_1a = new ArrayList<>(Arrays.asList(x, stone_one.get(1), -90)); // drive forward to stone position to pick up (X, 23, -90)
    public static final ArrayList<Integer> nine_1a = new ArrayList<>(Arrays.asList(x, five_1a.get(1), -90)); // back up from stone to allow for robot to turn (X, 28, -90)
    public static final ArrayList<Integer> ten_1a = new ArrayList<>(Arrays.asList(14, five_1a.get(1), 0)); // drive to other side to drop off stone
    public static final ArrayList<Integer> elev_1a = new ArrayList<>(Arrays.asList(3, five_1a.get(1), 0)); // park

    // -----------------position 1b
    public static final ArrayList<Integer> five_1b = new ArrayList<>(Arrays.asList(x, start_1blue.get(1) - 10, -90)); // back up to pos 1b Y position (X, 60, -90)
    public static final ArrayList<Integer> six_1b = new ArrayList<>(Arrays.asList(14, five_1b.get(1), 0)); // drive to other side to drop off stone
    public static final ArrayList<Integer> seven_1b = new ArrayList<>(Arrays.asList(x, five_1b.get(1), 0)); // drive to other stone X position backwards (X, 60, 0)
    public static final ArrayList<Integer> eight_1b = new ArrayList<>(Arrays.asList(x, stone_one.get(1), -90)); // drive forward to stone position to pick up (X, 23, -90)
    public static final ArrayList<Integer> nine_1b = new ArrayList<>(Arrays.asList(x, five_1b.get(1), -90)); // back up from stone to park b Y pos (X, 60, -90)
    public static final ArrayList<Integer> ten_1b = new ArrayList<>(Arrays.asList(14, five_1b.get(1), 0)); // drive to other side to drop off stone
    public static final ArrayList<Integer> elev_1b = new ArrayList<>(Arrays.asList(3, five_1b.get(1), 0)); // park

    // ----------------- position 2 -----------------
    public static final ArrayList<Integer> one_2 = new ArrayList<>(Arrays.asList(start_2.get(0), start_2.get(1) - 15, 90)); // drive forward a bit to catch tracker
    public static final ArrayList<Integer> two_2 = new ArrayList<>(Arrays.asList(start_2.get(0), found.get(1), 90)); // drive to foundation
    public static final ArrayList<Integer> three_2 = new ArrayList<>(Arrays.asList(start_2.get(0), 60, 90)); // drive backwards, dragging foundation to depot
    public static final ArrayList<Integer> four_2 = new ArrayList<>(Arrays.asList(start_2.get(0), three_2.get(1) + 5, 90)); // back up from foundation

    // ----------------- position 2a
    public static final ArrayList<Integer> five_2a = new ArrayList<>(Arrays.asList(0, four_2.get(1), 0)); // park

    // ----------------- position 2b
    public static final ArrayList<Integer> five_2b = new ArrayList<>(Arrays.asList(15, four_2.get(1), 0)); // drive forward
    public static final ArrayList<Integer> six_2b = new ArrayList<>(Arrays.asList(five_2b.get(0), 20, 0)); // strafe to park b Y position
    public static final ArrayList<Integer> seven_2b = new ArrayList<>(Arrays.asList(0, six_2b.get(1), 0)); // park

}
