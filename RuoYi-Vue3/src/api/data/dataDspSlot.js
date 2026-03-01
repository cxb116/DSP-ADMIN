import request from '@/utils/request'

// 查询预算报表列表
export function listDataDspSlot(query) {
  return request({
    url: '/system/dataDspSlot/list',
    method: 'get',
    params: query
  })
}

// 查询预算报表详细
export function getDataDspSlot(id) {
  return request({
    url: '/system/dataDspSlot/' + id,
    method: 'get'
  })
}

// 新增预算报表
export function addDataDspSlot(data) {
  return request({
    url: '/system/dataDspSlot',
    method: 'post',
    data: data
  })
}

// 修改预算报表
export function updateDataDspSlot(data) {
  return request({
    url: '/system/dataDspSlot',
    method: 'put',
    data: data
  })
}

// 删除预算报表
export function delDataDspSlot(id) {
  return request({
    url: '/system/dataDspSlot/' + id,
    method: 'delete'
  })
}
