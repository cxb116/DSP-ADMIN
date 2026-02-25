import request from '@/utils/request'

// 查询广告类型管理列表
export function listScene(query) {
  return request({
    url: '/advertise/scene/list',
    method: 'get',
    params: query
  })
}

// 查询广告类型管理详细
export function getScene(id) {
  return request({
    url: '/advertise/scene/' + id,
    method: 'get'
  })
}

// 新增广告类型管理
export function addScene(data) {
  return request({
    url: '/advertise/scene',
    method: 'post',
    data: data
  })
}

// 修改广告类型管理
export function updateScene(data) {
  return request({
    url: '/advertise/scene',
    method: 'put',
    data: data
  })
}

// 删除广告类型管理
export function delScene(id) {
  return request({
    url: '/advertise/scene/' + id,
    method: 'delete'
  })
}
