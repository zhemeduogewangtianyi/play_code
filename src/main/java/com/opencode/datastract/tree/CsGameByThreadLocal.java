package com.opencode.datastract.tree;

import java.util.concurrent.ThreadLocalRandom;


public class CsGameByThreadLocal {

    /*** 子弹数 */
    private static final int BULLET_NUMBER = 1500;

    /*** 杀人数 */
    private static final int KILLED_ENEMIES = 0;

    /*** 生存数 */
    private static final int LIVE_VALUE = 10;

    /*** 玩家数 */
    private static final int TOTAL_PLAYERS = 10;

    /*** 随机数用来展示每个对象的不同数据 */
    private static final ThreadLocalRandom RANDOM = ThreadLocalRandom.current();

    /*** 初始化子弹数 */
    private static final ThreadLocal<Integer> BULLET_NUMBER_THREAD_LOCAL = ThreadLocal.withInitial(() -> BULLET_NUMBER);

    /*** 初始化杀敌数 */
    private static final ThreadLocal<Integer> KILLED_ENEMIES_THREAD_LOCAL = ThreadLocal.withInitial(() -> KILLED_ENEMIES);

    /*** 初始化自己剩余的命数 */
    private static final ThreadLocal<Integer> LIVE_VALUE_THREAD_LOCAL = ThreadLocal.withInitial(() -> LIVE_VALUE);

    /*** 定义每个玩家 */
    private static class Player extends Thread {
        @Override
        public void run() {
            super.run();
            int bullets = BULLET_NUMBER_THREAD_LOCAL.get() - RANDOM.nextInt(BULLET_NUMBER);
            int killEnemies = KILLED_ENEMIES_THREAD_LOCAL.get() - RANDOM.nextInt(TOTAL_PLAYERS / 2);
            int liveValue = LIVE_VALUE_THREAD_LOCAL.get() - RANDOM.nextInt(LIVE_VALUE);
            System.out.println(getName() + ", BULLET_NUMBER is " + bullets);
            System.out.println(getName() + ", KILLED_ENEMIES is " + killEnemies);
            System.out.println(getName() + ", LIVE_VALUE is " + liveValue);

            BULLET_NUMBER_THREAD_LOCAL.remove();
            KILLED_ENEMIES_THREAD_LOCAL.remove();
            LIVE_VALUE_THREAD_LOCAL.remove();
        }
    }

    public static void main(String[] args) {
        for (int i = 0; i < TOTAL_PLAYERS; i++) {
            new Player().start();
        }
    }
}
