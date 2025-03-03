package com.payment.simulator.common.uid;

import com.payment.simulator.common.enums.PaymentSystem;

import java.time.LocalDateTime;

/**
 * * Twitter_Snowflake<br>
 * SnowFlake的结构如下(每部分用-分开):<br>
 * 0 - 0000000000 0000000000 0000000000 0000000000 0 - 00000 - 00000 - 000000000000 <br>
 * 1位标识，由于long基本类型在Java中是带符号的，最高位是符号位，正数是0，负数是1，所以id一般是正数，最高位是0<br>
 * 41位时间截(毫秒级)，注意，41位时间截不是存储当前时间的时间截，而是存储时间截的差值（当前时间截 - 开始时间截)
 * 得到的值），这里的的开始时间截，一般是我们的id生成器开始使用的时间，
 * 由我们程序来指定的（如下下面程序IdWorker类的startTime属性）。41位的时间截，
 * 可以使用69年，年T = (1L << 41) / (1000L * 60 * 60 * 24 * 365) = 69<br>
 * 10位的数据机器位，可以部署在1024个节点，包括5位datacenterId和5位workerId<br>
 * 12位序列，毫秒内的计数，12位的计数顺序号支持每个节点每毫秒(同一机器，同一时间截)产生4096个ID序号<br>
 * 加起来刚好64位，为一个Long型。<br>
 * SnowFlake的优点是，整体上按照时间自增排序，并且整个分布式系统内不会产生ID碰撞(由数据中心ID和机器ID作区分)，
 * 并且效率较高，经测试，SnowFlake每秒能够产生26万ID左右
 *-----------------------------------------------------------------------------------------
 * 调整后：
 * 0 - 0000000000 0000000000 0000000000 000000000 - 0000-00000 - 0000 - 00000000000 <br>
 * 1位符号位
 * 39位时间戳（毫秒数）：年T = (1L << 39) / (1000L * 60 * 60 * 24 * 365) = 17
 * 13位机器位： 4位数据中心位（最多支持16个数据中心）、5位服务id（最多支持32个服务）、4位实例id（最多支持单个服务16个实例）
 * 11位序列： 每个节点每毫秒(同一机器，同一时间截)产生2048（2^11=2048）个ID序号
 */
public class SnowflakeIdWorker {
    /**
     * 开始时间截 (2019-01-01)
     */
    private final long twepoch = 1546272000000L;

    /**
     * 数据标识id所占的位数
     */
    private final long dataCenterIdBits = 4L;

    /**
     * 机器id所占的位数(5位systemIdBits+4位instanceIdBits)
     */
    private final long workerIdBits = 9L;

    /**
     * 服务所占的位数(服务参考PaymentSystem)
     */
    private final long systemIdBits = 5L;

    /**
     * 服务对应容器实例所占的位数
     */
    private final long instanceIdBits = 4L;

    /**
     * 序列在id中占的位数(默认12位，调整后11位)
     */
    private final long sequenceBits = 11L;

    /**
     * 支持的最大数据标识id，结果是31
     */
    private final long maxDataCenterId = -1L ^ (-1L << dataCenterIdBits);

    /**
     * 支持的最大机器id，结果是31 (这个移位算法可以很快的计算出几位二进制数所能表示的最大十进制数)
     */
    private final long maxWorkerId = -1L ^ (-1L << workerIdBits);

    /**
     * 支持的最大服务数量
     */
    private final long maxSystemId = -1L ^ (-1L << systemIdBits);

    /**
     * 支持的最大服务容器实例
     */
    private final long maxInstanceId = -1L ^ (-1L << instanceIdBits);


    /**
     * 机器ID向左移11位
     */
    private final long workerIdShift = sequenceBits;

    /**
     * 数据标识id向左移20位(11+9)
     */
    private final long datacenterIdShift = sequenceBits + workerIdBits;

    /**
     * 时间截向左移23位(11+9+4)
     */
    private final long timestampLeftShift = sequenceBits + workerIdBits + dataCenterIdBits;

    /**
     * 系统服务
     */
    private final long systemIdShift = sequenceBits+instanceIdBits;
    /**
     * 系统服务
     */
    private final long  instatanceIdShift= sequenceBits;

    /**
     * 生成序列的掩码，这里为4095 (0b111111111111=0xfff=4095)
     */
    private final long sequenceMask = -1L ^ (-1L << sequenceBits);

    /**
     * 工作机器ID(0~31)
     */
    private long workerId;

    /**
     * 数据中心ID(0~31)
     */
    private long dataCenterId;
    /**
     * 服务ID
     */
    private long systemId;

    /**
     * 实例ID
     */
    private long instatanceId;

    /**
     * 毫秒内序列(0~4095)
     */
    private long sequence = 0L;

    /**
     * 上次生成ID的时间截
     */
    private long lastTimestamp = -1L;

    /**
     * 上次生成ID的时间截
     */
    private PaymentSystem paymentSystem = PaymentSystem.PAYMENT_UNKNOWN;



    public SnowflakeIdWorker(long datacenterId, long sysemId, long instanceId) {
        instanceId = instanceId % maxInstanceId;
        if (datacenterId > maxDataCenterId || datacenterId < 0){
            throw new IllegalArgumentException(String.format("datacenter Id can't be greater than %d or less than 0", datacenterId));
        }
        if (sysemId > maxSystemId || sysemId < 0){
            throw new IllegalArgumentException(String.format("system Id can't be greater than %d or less than 0", sysemId));
        }
        if (instanceId > maxInstanceId || instanceId < 0){
            throw new IllegalArgumentException(String.format("instance Id can't be greater than %d or less than 0", instanceId));
        }
        workerId = sysemId+instanceId;
        if (workerId > maxWorkerId || workerId < 0) {
            throw new IllegalArgumentException(String.format("worker Id can't be greater than %d or less than 0", maxWorkerId));
        }
        // this.workerId = workerId;
        this.dataCenterId = datacenterId;
        this.systemId = sysemId;
        this.instatanceId = instanceId;
        this.paymentSystem =  PaymentSystem.getPaymentSystemById(String.valueOf(sysemId));
    }




    /**
     * 获得下一个ID (该方法是线程安全的)
     *
     * @return SnowflakeId
     */
    public synchronized long nextId() {
        long timestamp = timeGen();

        //如果当前时间小于上一次ID生成的时间戳，说明系统时钟回退过这个时候应当抛出异常
        if (timestamp < lastTimestamp) {
            throw new RuntimeException(
                    String.format("Clock moved backwards.  Refusing to generate id for %d milliseconds", lastTimestamp - timestamp));
        }

        //如果是同一时间生成的，则进行毫秒内序列
        if (lastTimestamp == timestamp) {
            sequence = (sequence + 1) & sequenceMask;
            //毫秒内序列溢出
            if (sequence == 0) {
                //阻塞到下一个毫秒,获得新的时间戳
                timestamp = tilNextMillis(lastTimestamp);
            }
        }else {
            sequence = 0L;
        }

        //上次生成ID的时间截
        lastTimestamp = timestamp;

        //移位并通过或运算拼到一起组成64位的ID
//        return ((timestamp - twepoch) << timestampLeftShift)
//                | (dataCenterId << datacenterIdShift)
//                | (workerId << workerIdShift)
//                | sequence;
        return ((timestamp - twepoch) << timestampLeftShift)
                | (dataCenterId << datacenterIdShift)
                | (systemId << systemIdShift)
                | (instatanceId << instatanceIdShift)
                | sequence;
    }


    public synchronized String nextTypeId() {
        String suffix = "";
        switch (paymentSystem) {
            case PAYMENT_SERVICE:
                suffix = "10";
                break;
            case REFUND_SERVICE:
                suffix = "30";
                break;
            default:
                suffix = "00";
                break;
        }
        String timeAppend = String.format("%03d", LocalDateTime.now().getMonth().getValue())
                + String.format("%02d", LocalDateTime.now().getDayOfMonth());
        return String.format("%-29s", suffix + timeAppend + nextId()).replaceAll(" ", "0");
    }

    /**
     * 阻塞到下一个毫秒，直到获得新的时间戳
     *
     * @param lastTimestamp 上次生成ID的时间截
     * @return 当前时间戳
     */
    private long tilNextMillis(long lastTimestamp) {
        long timestamp = timeGen();
        while (timestamp <= lastTimestamp) {
            timestamp = timeGen();
        }
        return timestamp;
    }

    /**
     * 返回以毫秒为单位的当前时间
     *
     * @return 当前时间(毫秒)
     */
    private long timeGen() {
        return System.currentTimeMillis();
    }

    /**
     * 反推出服务ID
     *
     * @param id snowflake生成的id
     * @return 所属机器的id
     */
    public long inferSystemId(long id) {
        return id >> systemIdShift & ~(-1L << systemIdBits);
    }

    public long inferInstanceId(long id) {
        return id >> instatanceIdShift & ~(-1L << instanceIdBits);
    }
    /**
     * 反推出数据中心的id
     *
     * @param id snowflake生成的id
     * @return 所属数据中心的值
     */
    public long inferDataCenterId(long id) {
        return id >> datacenterIdShift & ~(-1L << dataCenterIdBits);
    }
    /**
     * 反推生成时间
     *
     * @param id snowflake生成的id
     * @return 生成的时间
     */
    public long inferGenerateDateTime(long id) {
        return (id >> timestampLeftShift & ~(-1L << 41L)) + twepoch;
    }


}
