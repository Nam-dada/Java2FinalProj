<script setup lang = "ts">
import { reactive, ref } from "vue";
import { Link, Search, Star, Delete } from "@element-plus/icons-vue";



</script>

<template>
  <el-row>
    <el-col :span="2"></el-col>
    <el-col :span="20">
      <el-card class="box-card" id="head" shadow="always" @click="">
        <h1>GitHub Repository Visualization</h1>
      </el-card>
    </el-col>
    <el-col :span="2"></el-col
    ></el-row>
  <el-row>
    <el-col :span="2"></el-col>
    <el-col :span="20">
      <el-card class="box-card" shadow="hover">
        <el-row :gutter="20" class="row">
          <el-col :span="2" :offset="0" style="text-align: center">
            <el-button @click="">
              <img src="src/assets/vue.svg" alt=""
              /></el-button>
          </el-col>
          <el-col :span="14" :offset="0"
          ><!-- input 输入框 -->
            <el-input
                placeholder="Please enter Public GitHub repository name (eg.)"
                id="input"
            /></el-col>
          <el-col :span="3" :offset="0"
          ><!-- 立即查看 -->
            <el-button :icon="Search">Query Now</el-button>
          </el-col>
          <el-col :span="3" :offset="0"
          ><!-- 添加到列表 -->
            <el-button :icon="Star" @click="">Add To List</el-button>
          </el-col>
        </el-row>
      </el-card>
    </el-col>
    <el-col :span="2"></el-col>
  </el-row>
  <el-row>
    <el-col :span="2"></el-col>
    <el-col :span="20">
      <el-card
          class="box-card radius"
          shadow="hover"
      >
        <el-row :gutter="20" class="row">
          <el-col
              :span="2"
              :offset="0"
              style="text-align: center"
              @click=""
          >
            <el-button>{{ 1 }}</el-button>
          </el-col>
          <el-col :span="14" :offset="0" @click="">
            <span class="name">{{  }}</span>
            <span class="ownner">Ownner: {{  }}</span>
          </el-col>
          <el-col :span="3" :offset="0"
          ><!-- 立即查看 -->
            <el-button :icon="Link" @click="">Go To Hub</el-button>
          </el-col>
          <el-col :span="3" :offset="0"
          ><!-- 添加到列表 -->
            <el-button :icon="Delete" @click=""
            >Delete This</el-button
            >
          </el-col>
        </el-row>
      </el-card>
    </el-col>
    <el-col :span="2"></el-col>
  </el-row>

</template>

<style lang="css" scoped>
.radius {
  border-radius: 0.6rem;
}

.name {
  margin-left: 0.5rem;
  font-size: 1.5rem;
  font-weight: 600;
  color: black;
  margin-right: 1rem;
}

.ownner {
  font-size: 0.9rem;
  font-weight: 400;
  color: grey;
  margin-right: 1rem;
}

.box-card {
  margin-top: 0.5rem;
  border-radius: 0.6rem;
}

#head {
  margin-top: 1.5rem;
  text-align: center;
  font-size: 1.5rem;
  border-radius: 0.6rem;
}

#input {
  width: 50vw;
  margin-top: 0;
  padding-top: 0;
}

.row {
  display: flex;
  flex-direction: row;
  justify-content: center;
  align-items: center;
}

h1 {
  margin: 0;
}
</style>
