package com.payment.simulator.common.enums;

/**
 * 描述信息
 *
 * 
 * @createTime 2021-11-01
 */
public enum ChargebackStatusCode {

    /**
     *An action on your side is required (you need to respond to the dispute). At this stage, your account gets debited the disputed amount.
     */
    EVIDENCE_REQUIRED,
    /**
     *We automatically resolve the dispute because it had already been refunded previously. There are no further financial implications.
     */
    RESOLVED,
    /**
     *The dispute is canceled by the issuing bank and your account is credited back of the amount of the dispute.
     *  You don’t need to take any further action.
     */
    CANCELED,
    /**
     *You have submitted evidence to defend against the dispute.
     *  Your evidence is now under review by our Disputes team.
     *  Once reviewed, the team will send it to the card scheme for review. No further action is required on your side.
     */
    EVIDENCE_UNDER_REVIEW,
    /**
     *The issuing bank has rejected your evidence and you have lost the dispute. There are no further financial implications.
     */
    LOST,
    /**
     *
     * The issuing bank has accepted your evidence and you have won the dispute.
     * Your account has been credited back of the amount of the dispute.
     */
    WON,
    /**
     *The time limit to take action is met.
     * There is no further implication and you can’t respond to the dispute anymore.
     */
    EXPIRED,
    /**
     *
     * You have accepted the dispute. There is no further implication or action required.
     */
    ACCEPTED;
}
