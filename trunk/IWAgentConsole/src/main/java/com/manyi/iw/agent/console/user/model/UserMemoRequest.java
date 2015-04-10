package com.manyi.iw.agent.console.user.model;

import lombok.Data;

import com.manyi.iw.agent.console.entity.Entity;

@Data
public class UserMemoRequest extends Entity {
    private Long userId;
}
