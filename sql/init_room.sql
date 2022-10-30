-- 初始化1~5号楼，每层楼10个房间，每个房间的大小为2~7
DELIMITER //

drop procedure if exists func_init_room;
# 初始化房间
CREATE procedure func_init_room()
BEGIN
    DECLARE val_build_index, var_floor_index, var_room_index, var_bed_cnt_all,var_room_id INT; # 申明楼号, 楼层. 房间号
    SET val_build_index = 1; # 最小楼号
    WHILE val_build_index <= 5
        DO
            SET var_floor_index = 1; # 最小层数
            WHILE var_floor_index <= 5
                DO
                    SET var_room_index = 1;
                    WHILE var_room_index <= 10
                        DO
                            SET var_room_id = val_build_index * 1000 + var_floor_index * 100 + var_room_index;
                            SET var_bed_cnt_all = (RAND() * (10 - 5) + 2);

                            INSERT INTO room
                            (room_id, room_sex, room_available, bed_cnt_all, bet_cnt_available, bed_cnt_free, floor_id)
                            VALUES (var_room_id, var_floor_index mod 2, 1, var_bed_cnt_all, var_bed_cnt_all,
                                    var_bed_cnt_all, val_build_index);
                            SET var_room_index = var_room_index + 1; # 循环一次,i加1
                        END WHILE;
                    SET var_floor_index = var_floor_index + 1;
                END WHILE; # 结束while循环
            SET val_build_index = val_build_index + 1;
        END WHILE;

END;

DROP procedure IF EXISTS append_beds;
-- 初始化床位
CREATE PROCEDURE append_beds()
BEGIN
    -- 该变量用于标识是否还有数据需遍历
    DECLARE flag INT DEFAULT 0;
    -- 创建一个变量用来存储遍历过程中的值
    DECLARE var_room_id,var_bed_num,var_i INT;
    -- 查询出需要遍历的数据集合,这里指定数据库下面所有表明
    DECLARE var_room_id_bed_num_list CURSOR FOR select id, bed_cnt_all from room;
    -- 查询是否有下一个数据，没有将标识设为1，相当于hasNext
    DECLARE CONTINUE HANDLER FOR NOT FOUND SET flag = 1;
    -- 打开游标
    OPEN var_room_id_bed_num_list;
    -- 取值设置到临时变量中，我这里就一个字段
    -- 注意：多个字段取值，变量名不要和返回的列名同名，变量顺序要和sql结果列的顺序一致
    FETCH var_room_id_bed_num_list INTO var_room_id,var_bed_num;
    -- 遍历未结束就一直执行
    WHILE flag != 1
        DO
            -- targetSQL #你想要执行的目标功能，这里可以写多个SQL
            SET var_i = 0;
            WHILE var_i < var_bed_num
                DO
                    INSERT INTO room_bed_user
                        (room_id, bed_id)
                    VALUES (var_room_id, var_i);
                    SET var_i = var_i + 1;
                end while;
            -- 一定要记得把游标向后移一位，这个坑我替各位踩过了，不需要再踩了
            FETCH var_room_id_bed_num_list INTO var_room_id,var_bed_num;
            -- 当s等于1时表明遍历以完成，退出循环
        END WHILE;
    -- 关闭游标
    CLOSE var_room_id_bed_num_list;
END;
CALL func_init_room();
# 调用存储过程
-- 执行存储过程
call append_beds(); #
// # 结束定义语句
DELIMITER ; # 重新将分隔符设置为;