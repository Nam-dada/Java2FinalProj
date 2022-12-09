#!/usr/bin/env python3
# -*- coding: utf-8 -*-
# @Time : 2020/8/26 14:48
# @Author : way
# @Site : 
# @Describe:

import json
import numpy as np


class SourceDataDemo:

    def __init__(self):
        self.title = '大数据可视化展板通用模板'
        self.counter = {'name': '2018年总收入情况', 'value': 12581189}
        self.counter2 = {'name': '2018年总支出情况', 'value': 3912410}
        self.echart1_data = {
            'title': '行业分布',
            'data': [
                {"name": "商超门店", "value": 47},
                {"name": "教育培训", "value": 52},
                {"name": "房地产", "value": 90},
                {"name": "生活服务", "value": 84},
                {"name": "汽车销售", "value": 99},
                {"name": "旅游酒店", "value": 37},
                {"name": "五金建材", "value": 2},
            ]
        }
        self.echart2_data = {
            'title': '省份分布',
            'data': [
                {"name": "浙江", "value": 47},
                {"name": "上海", "value": 52},
                {"name": "江苏", "value": 90},
                {"name": "广东", "value": 84},
                {"name": "北京", "value": 99},
                {"name": "深圳", "value": 37},
                {"name": "安徽", "value": 150},
            ]
        }
        self.echarts3_1_data = {
            'title': '年龄分布',
            'data': [
                {"name": "0岁以下", "value": 47},
                {"name": "20-29岁", "value": 52},
                {"name": "30-39岁", "value": 90},
                {"name": "40-49岁", "value": 84},
                {"name": "50岁以上", "value": 99},
            ]
        }
        self.echarts3_2_data = {
            'title': '职业分布',
            'data': [
                {"name": "电子商务", "value": 10},
                {"name": "教育", "value": 20},
                {"name": "IT/互联网", "value": 20},
                {"name": "金融", "value": 30},
                {"name": "学生", "value": 40},
                {"name": "其他", "value": 50},
            ]
        }
        self.echarts3_3_data = {
            'title': '兴趣分布',
            'data': [
                {"name": "汽车", "value": 4},
                {"name": "旅游", "value": 5},
                {"name": "财经", "value": 9},
                {"name": "教育", "value": 8},
                {"name": "软件", "value": 9},
                {"name": "其他", "value": 9},
            ]
        }
        self.echart4_data = {
            'title': '时间趋势',
            'data': [
                {"name": "安卓", "value": [3, 4, 3, 4, 3, 4, 3, 6, 2, 4, 2, 4, 3, 4, 3, 4, 3, 4, 3, 6, 2, 4, 4]},
                {"name": "IOS", "value": [5, 3, 5, 6, 1, 5, 3, 5, 6, 4, 6, 4, 8, 3, 5, 6, 1, 5, 3, 7, 2, 5, 8]},
            ],
            'xAxis': ['01', '02', '03', '04', '05', '06', '07', '08', '09', '11', '12', '13', '14', '15', '16', '17',
                      '18', '19', '20', '21', '22', '23', '24'],
        }
        self.echart5_data = {
            'title': '省份TOP',
            'data': [
                {"name": "浙江", "value": 2},
                {"name": "上海", "value": 3},
                {"name": "江苏", "value": 3},
                {"name": "广东", "value": 9},
                {"name": "北京", "value": 15},
                {"name": "深圳", "value": 18},
                {"name": "安徽", "value": 20},
                {"name": "四川", "value": 13},
            ]
        }
        self.echart6_data = {
            'title': 'Empty',
            'data': [
                {"name": "var1", "value": 80, "value2": 20, "color": "01", "radius": ['59%', '70%']},
                {"name": "var2", "value": 70, "value2": 30, "color": "02", "radius": ['49%', '60%']},
                {"name": "var3", "value": 65, "value2": 35, "color": "03", "radius": ['39%', '50%']},
                {"name": "var4", "value": 60, "value2": 40, "color": "04", "radius": ['29%', '40%']},
                {"name": "var5", "value": 50, "value2": 50, "color": "05", "radius": ['20%', '30%']},
            ]
        }
        self.map_1_data = {
            'symbolSize': 100,
            'data': []
        }

    def loadfile(self, name):
        with open(name, 'r', encoding='utf-8') as f:
            contributors, issues, releases, commits = f.read().split('-spy raye ace-')
        contributors = contributors.split('\n')[:-1]
        issues = issues.split('\n')[1:-1]
        releases = releases.split('\n')[1:-1]
        commits = commits.split('\n')[1:-1]
        self.title = 'Repo name:' + name.replace('.txt', '') + ' Data date:' + contributors[0].split(':')[1]
        self.counter = {'name': 'Count of developers', 'value': int(contributors[-1])}
        self.counter2 = {'name': 'Count of releases', 'value': int(releases[0].split(':')[-1])}

        self.echart1_data = {
            'title': 'Top 6 commits\' developers',
            'data': list()
        }
        for i in range(1, 7):
            temp_contri = contributors[i].split(',')
            tmp = {'name': str(temp_contri[0].split('=')[1]), 'value': int(temp_contri[1].split('=')[1][:-1])}
            self.echart1_data['data'].append(tmp)

        self.echart2_data = {
            'title': 'Typical handling of issue resolution time',
            'data': []
        }

        total_time = []
        op_cnt = int(issues[0].split(':')[1])
        close_cnt = int(issues[1].split(':')[1])
        issues = issues[2:]
        for isu in issues:
            if isu[-3:-1] != 'No':
                temp_idx = isu.rfind('=')
                isu_time = isu[temp_idx + 1: -1].split('Minutes')[0].replace(' ', '').split('Hours')
                temp_time = float(isu_time[0].split('Days')[0]) * 24 + float(isu_time[0].split('Days')[1]) + float(isu_time[1]) / 60
                total_time.append(temp_time)
        self.echart2_data['data'].append({'name': 'avg time', 'value': int(np.mean(total_time))})
        self.echart2_data['data'].append(
            {'name': 'range of time', 'value': int(np.max(total_time) - np.min(total_time))})
        self.echart2_data['data'].append({'name': 'var of time', 'value': int(np.var(total_time))})

        self.echarts3_1_data = {
            'title': 'Count of issues',
            'data': [
                {"name": "open", "value": op_cnt},
                {"name": "closed", "value": close_cnt},
            ]
        }

        # 搁置 3_2 和 3_3

        self.echart4_data = {
            'title': 'Time distribution of commits',
            'data': [],
            'xAxis': ['00', '01', '02', '03', '04', '05', '06', '07', '08', '09', '11', '12', '13', '14', '15', '16',
                      '17', '18', '19', '20', '21', '22', '23'],
        }

        weekday_time = [0 for x in range(0, 24)]
        weekend_time = [0 for x in range(0, 24)]
        Morning = int(commits[2].split(':')[1])  # (6-11]
        Noon = int(commits[3].split(':')[1])  # (11-13]
        Afternoon = int(commits[4].split(':')[1])  # (13-18]
        Evening = int(commits[5].split(':')[1])  # (18-22]
        Night = int(commits[6].split(':')[1])  # (22-6]
        Weekday = int(commits[8].split(':')[1])
        Weekend = int(commits[9].split(':')[1])

        self.echarts3_2_data = {
            'title': 'commit日分布',
            'data': [
                {"name": "Morning", "value": int(Morning)},
                {"name": "Noon", "value": int(Noon)},
                {"name": "Afternoon", "value": int(Afternoon)},
                {"name": "Evening", "value": int(Evening)},
                {"name": "Night", "value": int(Night)},
            ]
        }

        self.echarts3_3_data = {
            'title': 'commit周分布',
            'data': [
                {"name": "Weekday", "value": int(Weekday)},
                {"name": "Weekend", "value": int(Weekend)},
            ]
        }

        commits = commits[10:]
        for com in commits:
            c_time, c_type = com.split(' ')[2:-1]
            hour, _, _ = c_time.split(':')
            if c_type.split('=')[1] == 'Weekday':
                weekday_time[int(hour)] += 1
            else:
                weekend_time[int(hour)] += 1
        self.echart4_data['data'].append({'name': 'Cnt of commits in weekday', 'value': weekday_time})
        self.echart4_data['data'].append({'name': 'Cnt of commits in weekend', 'value': weekend_time})

        self.echart5_data = {
            'title': 'Commits\' diff between releases',
            'data': []
        }

        releases = releases[1:]
        for i in range(0, 6):
            rel = releases[i]
            l = rel.find('=')
            r = rel.find(',')
            r_name = rel[l + 1: r]
            rr = rel.rfind('=')
            count = rel[rr + 1: -1]
            self.echart5_data['data'].append({'name': r_name, 'value': int(count)})

    @property
    def echart1(self):
        data = self.echart1_data
        echart = {
            'title': data.get('title'),
            'xAxis': [i.get("name") for i in data.get('data')],
            'series': [i.get("value") for i in data.get('data')]
        }
        return echart

    @property
    def echart2(self):
        data = self.echart2_data
        echart = {
            'title': data.get('title'),
            'xAxis': [i.get("name") for i in data.get('data')],
            'series': [i.get("value") for i in data.get('data')]
        }
        return echart

    @property
    def echarts3_1(self):
        data = self.echarts3_1_data
        echart = {
            'title': data.get('title'),
            'xAxis': [i.get("name") for i in data.get('data')],
            'data': data.get('data'),
        }
        return echart

    @property
    def echarts3_2(self):
        data = self.echarts3_2_data
        echart = {
            'title': data.get('title'),
            'xAxis': [i.get("name") for i in data.get('data')],
            'data': data.get('data'),
        }
        return echart

    @property
    def echarts3_3(self):
        data = self.echarts3_3_data
        echart = {
            'title': data.get('title'),
            'xAxis': [i.get("name") for i in data.get('data')],
            'data': data.get('data'),
        }
        return echart

    @property
    def echart4(self):
        data = self.echart4_data
        echart = {
            'title': data.get('title'),
            'names': [i.get("name") for i in data.get('data')],
            'xAxis': data.get('xAxis'),
            'data': data.get('data'),
        }
        return echart

    @property
    def echart5(self):
        data = self.echart5_data
        echart = {
            'title': data.get('title'),
            'xAxis': [i.get("name") for i in data.get('data')],
            'series': [i.get("value") for i in data.get('data')],
            'data': data.get('data'),
        }
        return echart

    @property
    def echart6(self):
        data = self.echart6_data
        echart = {
            'title': data.get('title'),
            'xAxis': [i.get("name") for i in data.get('data')],
            'data': data.get('data'),
        }
        return echart

    @property
    def map_1(self):
        data = self.map_1_data
        echart = {
            'symbolSize': data.get('symbolSize'),
            'data': data.get('data'),
        }
        return echart


class SourceData(SourceDataDemo):

    def __init__(self):
        """
        按照 SourceDataDemo 的格式覆盖数据即可
        """
        super().__init__()
        self.loadfile('spring-cloud.txt')


class CorpData(SourceDataDemo):

    def __init__(self):
        """
        按照 SourceDataDemo 的格式覆盖数据即可
        """
        super().__init__()
        self.loadfile('arthas.txt')


class JobData(SourceDataDemo):

    def __init__(self):
        """
        按照 SourceDataDemo 的格式覆盖数据即可
        """
        super().__init__()
        self.loadfile('fastjson.txt')
